package com.example.android_practice_1.push_notifications.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_practice_1.R
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging

class PushNotificationPracticeActivity : AppCompatActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, you can proceed with notifications
            } else {
                // Permission denied, show a message to the user
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_push_notification_practice)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestNotificationPermission()

        Firebase.messaging.subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Log.d("check_firebase_msg", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
        Log.d("check_product_id","${intent.hasExtra("product_id")}")
        if (intent.hasExtra("product_id")){
            for (key in intent.extras!!.keySet()){
                Log.d("check_noti_data","key = $key value = ${intent.extras!!.getString(key)}")
            }
        }

    }

    private fun requestNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                Toast.makeText(this, "already granted", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Request the permission
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}