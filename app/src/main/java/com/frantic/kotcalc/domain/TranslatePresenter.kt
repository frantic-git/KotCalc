package com.frantic.kotcalc.domain

import android.util.Log
import com.frantic.kotcalc.data.Floof
import com.frantic.kotcalc.data.PostsRepository
import com.frantic.kotcalc.presentation.TranslateView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class TranslatePresenter() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    constructor(_mView : TranslateView) : this(){
        mView = _mView
    }

    lateinit var mView: TranslateView

    fun btnTranslateClick() {
        doWork()
    }

    fun btnSaveClick(){

    }

    fun doWork() {
        var result = 1.0
        var listFloof: List<Floof>? = null
        viewModelScope.launch {
            withContext(IO) {
                for (i in 1..5) {
                    result += i * i
                }

                listFloof = PostsRepository().doWork(PostsRepository.Params()).posts
            }
        }
        Log.d("calc_log", " result = $result")
        if (listFloof != null){
            Log.d("calc_log", " image = ${listFloof!![0].image}")
        }
    }

    fun cancelJob() {
        viewModelJob.cancel()
    }
}