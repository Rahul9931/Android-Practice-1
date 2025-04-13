package com.example.android_practice_1.retrofit_practice.networking.api_service

import android.os.Build
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
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
                .addInterceptor(ResponseLoggingInterceptor()) // Response logging interceptor
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
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

    private class ResponseLoggingInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)

            // Use `peekBody()` to avoid consuming the response body
            val responseBodyString = response.peekBody(Long.MAX_VALUE).string()
            Log.d("check_RetrofitClient", "Response body --> $responseBodyString")

            // Parse JSON safely
            val jsonBody = try {
                JSONObject(responseBodyString)
            } catch (e: Exception) {
                Log.d("check_RetrofitClient","parsing failure occur received empty jsonBody")
                JSONObject()
            // Return an empty JSON object in case of parsing failure
            }
            Log.d("check_RetrofitClient", "Response body json --> $jsonBody")
            Log.d("check_RetrofitClient", "title --> ${jsonBody.optBoolean("completed")}")
            var successKey = jsonBody.optBoolean("completed")

            // Check if the response code is not 200
            if (response.code != 200 || successKey == false) {
                // Prepare the error log request body
                val errorLogBody = JSONObject().apply {
                    put("Error", JSONObject().apply {
                        put("url", request.url.toString())
                        put("error", "some error")
                        put("status_code", response.code)
                        put("os", "android")
                        put("android_version", Build.VERSION.RELEASE)
                        put("device_name", Build.MODEL)
                    })
                    put("type", if (response.code != 200) "critical" else "warning")
                }

                // Log the error log request body
                Log.d("check_RetrofitClient", "Error log request body --> $errorLogBody")

                // Make the error log API call
                logErrorToServer(errorLogBody)
            }

            // Return the original response to avoid breaking the chain
            return response
        }

        private fun logErrorToServer(errorLogBody: JSONObject) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY  // Logs request & response body
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val requestBody = FormBody.Builder()
            // Add the "Error" field as a JSON string
                .add("Error", errorLogBody.getJSONObject("Error").toString())
            // Add the "type" field
                .add("type", errorLogBody.getString("type"))
                .build()

            val request = Request.Builder()
                .url("https://webtest.maqadhe.com/mobikulhttp/app/errorlog")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("check_errorlog", "Failed to log error: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {  // Ensure response is closed properly
                        val responseBody = response.body?.string() ?: "No response body"
                        Log.d("check_errorlog", "Error logged successfully: $responseBody")
                    }
                }
            })
        }

    }
}