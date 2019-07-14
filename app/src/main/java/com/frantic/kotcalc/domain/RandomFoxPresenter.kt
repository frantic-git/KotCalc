package com.frantic.kotcalc.domain

import com.frantic.kotcalc.data.RandomFoxAPI
import com.frantic.kotcalc.presentation.RandomFoxView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomFoxPresenter() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val baseUrl = "https://randomfox.ca/"
    private lateinit var gsonRandomFoxAPI: RandomFoxAPI
    private lateinit var responseBodyRandomFoxAPI: RandomFoxAPI

    lateinit var mView: RandomFoxView

    constructor(_mView : RandomFoxView) : this(){
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

    fun btnGetRandomFoxOnClick(){

        viewModelScope.launch {
            val imageLink = gsonRandomFoxAPI
                .getFloof()
                .body()
                ?.image

            if(imageLink != null){
                val imageName = imageLink.substringAfter("images/")

                try{
                    val byteArray = responseBodyRandomFoxAPI
                        .getImage(imageName)
                        .body()
                        ?.bytes()
                    if(byteArray != null) {
                        mView.showFox(byteArray)
                    }
                } catch (e: Exception) {
                    println(e.localizedMessage)
                }
            }
        }

    }

    fun btnSaveOnClick(){

    }

    fun cancelAllWork() {
        viewModelJob.cancel()
    }
}