package com.example.android_practice_1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_practice_1.databinding.ActivityUiCheckBinding

class Ui_check : AppCompatActivity() {
    private val binding:ActivityUiCheckBinding by lazy {
        ActivityUiCheckBinding.inflate(layoutInflater)
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
        var list = mutableListOf<Int>()
        for (i in 0..30){
            list.add(i)
        }
        setAdapter(list)
    }

    private fun setAdapter(list: MutableList<Int>) {
        binding.rvCutom.layoutManager = LinearLayoutManager(this)
        val customAdapter = CustomAdapter(this,list)
        binding.rvCutom.adapter = customAdapter
    }
}