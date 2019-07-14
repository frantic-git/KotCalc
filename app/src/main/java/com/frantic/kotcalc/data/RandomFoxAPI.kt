package com.frantic.kotcalc.data

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RandomFoxAPI {

    @GET("floof")
    suspend fun getFloof(): Response<Floof>

    @GET("images/{imageName}")
    suspend fun getImage(@Path("imageName") imageName: String): Response<ResponseBody>

}