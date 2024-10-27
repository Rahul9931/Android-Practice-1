package com.example.android_practice_1.helper

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_practice_1.R
import com.example.android_practice_1.databinding.ActivityHelperBinding

class HelperActivity : AppCompatActivity() {

    private val binding:ActivityHelperBinding by lazy {
        ActivityHelperBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnHelper.setOnClickListener {
            val time = Helper().getCurrentDateTime()
            Log.d("check_time","$time")
        }

    }
}