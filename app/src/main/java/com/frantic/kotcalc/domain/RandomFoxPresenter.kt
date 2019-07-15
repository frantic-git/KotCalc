package com.frantic.kotcalc.domain

import com.frantic.kotcalc.App
import com.frantic.kotcalc.data.RandomFoxAPI
import com.frantic.kotcalc.data.db.entity.FoxEntity
import com.frantic.kotcalc.presentation.RandomFoxView
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomFoxPresenter() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val baseUrl = "https://randomfox.ca/"
    private lateinit var gsonRandomFoxAPI: RandomFoxAPI
    private lateinit var responseBodyRandomFoxAPI: RandomFoxAPI

    lateinit var mView: RandomFoxView
    var byteArray: ByteArray? = null
    var imageName: String? = null

    constructor(_mView: RandomFoxView) : this() {
        mView = _mView

        gsonRandomFoxAPI = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomFoxAPI::class.java)

        responseBodyRandomFoxAPI = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
            .create(RandomFoxAPI::class.java)
    }

    fun btnGetRandomFoxOnClick() {

        viewModelScope.launch {
            val imageLink = gsonRandomFoxAPI
                .getFloof()
                .body()
                ?.image

            if (imageLink != null) {
                imageName = imageLink.substringAfter("images/")

                try {
                    byteArray = responseBodyRandomFoxAPI
                        .getImage(imageName!!)
                        .body()
                        ?.bytes()
                    if (byteArray != null) {
                        mView.showFox(byteArray!!)
                    }
                } catch (e: Exception) {
                    println(e.localizedMessage)
                }
            }
        }
    }

    fun btnSaveOnClick() {
        if (imageName != null) {
            GlobalScope.launch(Dispatchers.IO) {
                App.foxDataBase?.foxDao()?.insertFox(FoxEntity(null, imageName!!))
            }
        }
    }

    fun cancelAllWork() {
        viewModelJob.cancel()
    }
}