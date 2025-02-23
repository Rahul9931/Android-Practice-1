package com.example.android_practice_1.retrofit_practice.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.android_practice_1.R
import com.example.android_practice_1.constants.ApplicationConstants
import com.example.android_practice_1.databinding.ActivityRetrofitPracticeBinding
import com.example.android_practice_1.retrofit_practice.model.LoginResponse
import com.example.android_practice_1.retrofit_practice.model.User
import com.example.android_practice_1.retrofit_practice.networking.api_service.ProductApiService
import com.example.android_practice_1.retrofit_practice.networking.api_service.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitPracticeActivity : AppCompatActivity() {
    lateinit var binding: ActivityRetrofitPracticeBinding
    private lateinit var apiService: ProductApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_retrofit_practice)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val retrofit = RetrofitClient.getClient("")
        apiService = retrofit.create(ProductApiService::class.java)

        binding.button.setOnClickListener {
            Log.d("check_button","button clicked")
            // Get the Retrofit instance with the token
            val apiService = RetrofitClient.getClient("48v3di8cem4uoqvr3vsi7evbt0").create(ProductApiService::class.java)
            val call = apiService.postRequest(
                ApplicationConstants().API_KEY,
                ApplicationConstants().API_PASSWARD,
                "",
                "en",
                "USD"
            )

            call.enqueue(object : Callback<LoginResponse>{
                override fun onResponse(p0: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful){
                        Log.d("check_response","${response.body()}")
                    }
                    else{
                        Log.d("check_else_error","${response.body()}")
                    }

                }

                override fun onFailure(p0: Call<LoginResponse>, p1: Throwable) {
                    Log.d("check_error","${p1.message}")
                }

            })


        }

        binding.button2.setOnClickListener {


            // Call the API
            fetchTodo()

//            val apiService = RetrofitClient.getClient("iewfgie").create(ProductApiService::class.java)
//            val call = apiService.getUser()
//            call.enqueue(object : Callback<User>{
//                override fun onResponse(p0: Call<User>, response: Response<User>) {
//                    if (response.isSuccessful){
//                        Log.d("check_response","${response.body()}")
//                    }
//                    else{
//                        Log.d("check_else_error","${response.message()}")
//                    }
//                }
//
//                override fun onFailure(p0: Call<User>, p1: Throwable) {
//                    Log.d("check_error","${p1.message}")
//                }
//
//            })
        }

        binding.button3.setOnClickListener {
            fetchTodo4()
        }
        }

    fun fetchTodo() {
        lifecycleScope.launch {
            try {
                val response = apiService.getTodo()
                if (response.isSuccessful) {
                    val todo = response.body()
                    Log.d("API_RESPONSE", "Todo: ${todo?.title}, Completed: ${todo?.completed}")
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception: ${e.message}")
            }
        }
    }

    fun fetchTodo4() {
        lifecycleScope.launch {
            try {
                val response = apiService.getTodo4()
                if (response.isSuccessful) {
                    val todo = response.body()
                    Log.d("API_RESPONSE", "Todo: ${todo?.title}, Completed: ${todo?.completed}")
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception: ${e.message}")
            }
        }
    }

}