package com.example.android_practice_1.helper

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Helper {

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        Log.d("check_dateformat","$dateFormat")
        val currentDate = Date()
        Log.d("check_current_date","$currentDate")
        return dateFormat.format(currentDate)
    }
}