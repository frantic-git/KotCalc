package com.frantic.kotcalc.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class PostsRepository {

    suspend fun doWork(params: Params): Result {
        val retrofitFloof = Retrofit
            .Builder()
            .baseUrl("https://randomfox.ca/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RetrofitFloof::class.java)
        val result = retrofitFloof
            .getFloofs()
            .await()

        return Result(result.body())
    }

    class Params
    data class Result(val posts: List<Floof>?)
}