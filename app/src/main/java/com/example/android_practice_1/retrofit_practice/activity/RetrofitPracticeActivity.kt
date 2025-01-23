package com.example.android_practice_1.retrofit_practice.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.android_practice_1.R
import com.example.android_practice_1.constants.ApplicationConstants
import com.example.android_practice_1.databinding.ActivityRetrofitPracticeBinding
import com.example.android_practice_1.retrofit_practice.model.LoginResponse
import com.example.android_practice_1.retrofit_practice.model.User
import com.example.android_practice_1.retrofit_practice.networking.api_service.ProductApiService
import com.example.android_practice_1.retrofit_practice.networking.api_service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitPracticeActivity : AppCompatActivity() {
    lateinit var binding: ActivityRetrofitPracticeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_retrofit_practice)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
            val apiService = RetrofitClient.getClient("iewfgie").create(ProductApiService::class.java)
            val call = apiService.getUser()
            call.enqueue(object : Callback<User>{
                override fun onResponse(p0: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        Log.d("check_response","${response.body()}")
                    }
                    else{
                        Log.d("check_else_error","${response.message()}")
                    }
                }

                override fun onFailure(p0: Call<User>, p1: Throwable) {
                    Log.d("check_error","${p1.message}")
                }

            })
        }
        }

    private fun callApi(call: Call<LoginResponse>) {


    }

}