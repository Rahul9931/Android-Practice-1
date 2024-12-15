package com.example.android_practice_1.scheduling.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_practice_1.R
import com.example.android_practice_1.databinding.ActivityOneBinding
import com.example.android_practice_1.scheduling.receiver.AlarmReceiver

class OneActivity : AppCompatActivity() {
    val binding : ActivityOneBinding by lazy {
        ActivityOneBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_one)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSetAlarm.setOnClickListener {
            setAlarm()
        }
    }

    private fun setAlarm() {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.SECOND, 10)
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(this, "Alarm set for 10 second", Toast.LENGTH_SHORT).show()
    }
}