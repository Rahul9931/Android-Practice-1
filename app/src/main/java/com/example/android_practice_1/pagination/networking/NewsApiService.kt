package com.example.android_practice_1.pagination.networking

import com.example.android_practice_1.mvvm.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines?country=us&apiKey=29337f0874714e63b8c7e29abe9c28a7")
    suspend fun getNews(
        @Query("page") page:Int,
        @Query("pageSize") pageSize:Int

    ): News
}