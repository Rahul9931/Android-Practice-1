package com.example.android_practice_1.search_pagination_2.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @POST("mobikul/search")
    suspend fun searchProducts(
        @Body request: SearchRequest,
        @Header("Authorization") auth: String = "Basic NDhmMC1iNDM0LWFiZTk0NTdiMTk2NTo0OGYwLWI0MzQtYWJlOTQ1N2IxOTY1",
        @Header("Login") login: String = "eyJsb2dpbiI6Im9kb28ud2Via3VsQHdlYmt1bC5jb20iLCJwd2QiOiJtb2Jpa3VsZGVtbyJ9"
    ): Response<SearchResponse>
}

data class SearchRequest(
    val offset: Int,
    val search: String
)