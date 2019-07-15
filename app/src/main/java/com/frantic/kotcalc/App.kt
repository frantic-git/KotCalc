package com.frantic.kotcalc

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.frantic.kotcalc.data.db.FoxDataBase

class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
        var foxDataBase: FoxDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        foxDataBase = FoxDataBase.getInstance(context = context!!)
    }
}