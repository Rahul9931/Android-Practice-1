package com.example.android_practice_1.retrofit_practice.networking.api_service

import com.example.android_practice_1.constants.ApplicationConstants
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var retrofit: Retrofit? = null

    // Token for authentication
    private var authToken: String? = null

    // Function to initialize Retrofit with Token Management
    fun getClient(token: String): Retrofit {
        if (retrofit == null || authToken != token) {
            authToken = token

            // Logging interceptor to log requests and responses
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // OkHttpClient with token management
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(AuthTokenInterceptor(token)) // Token interceptor
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                //.client(okHttpClient)
                .build()
        }
        return retrofit!!
    }

    // Interceptor to add the token to each request's headers
    private class AuthTokenInterceptor(private val token: String) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token") // Add Bearer token
                .build()
            return chain.proceed(request)
        }
    }

}