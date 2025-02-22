package com.example.android_practice_1.retrofit_practice.networking.api_service

import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ErrorLogApiService {
    @POST("mobikulhttp/app/errorlog")
    fun logError(@Body body: FormBody): Call<ResponseBody>
}