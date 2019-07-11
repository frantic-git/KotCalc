package com.frantic.kotcalc.data

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitFloof {

    @GET("floof")
    fun getFloofs(): Deferred<Response<List<Floof>>>

}