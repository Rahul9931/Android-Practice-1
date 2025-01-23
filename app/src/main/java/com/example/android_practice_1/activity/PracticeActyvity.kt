package com.example.android_practice_1.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android_practice_1.R
import com.example.android_practice_1.databinding.ActivityPracticeActyvityBinding
import com.example.android_practice_1.model.Quote
import com.example.android_practice_1.view_model.PracticeViewModel

class PracticeActyvity : AppCompatActivity() {
    /*lateinit var practiceViewModel: PracticeViewModel
    val btnUpdate:Button get() = findViewById(R.id.btn_fact)
    val fact:TextView get() = findViewById(R.id.txt_fact)*/

    lateinit var binding:ActivityPracticeActyvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_practice_actyvity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.quote = Quote("My personal quote","Rahul saini")

        /*practiceViewModel = ViewModelProvider(this).get(PracticeViewModel::class.java)
        practiceViewModel.factLiveData.observe(this, Observer{
            fact.text = it
        })

        btnUpdate.setOnClickListener {
            practiceViewModel.updateFact()
        }*/

    }
}