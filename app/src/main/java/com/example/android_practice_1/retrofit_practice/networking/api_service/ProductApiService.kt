package com.example.android_practice_1.retrofit_practice.networking.api_service

import com.example.android_practice_1.retrofit_practice.model.LoginResponse
import com.example.android_practice_1.retrofit_practice.model.TodoUser
import com.example.android_practice_1.retrofit_practice.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ProductApiService {
    @FormUrlEncoded
    @Headers("Accept: application/json","Content-Type:application/json")
    @POST("index.php?route=api/wkrestapi/common/apiLogin")
    fun postRequest(
        @Field("apiKey") apiKey:String,
        @Field("apiPassword") passward:String,
        @Field("customer_id") customer_id:String,
        @Field("language") language:String,
        @Field("currency") currency:String
    ):retrofit2.Call<LoginResponse>

    @GET("posts/1")
    fun getUser(): Call<User>

    @GET("todos/1")
    suspend fun getTodo(): Response<TodoUser>

    @GET("todos/2000")
    suspend fun getTodo4(): Response<TodoUser>

}