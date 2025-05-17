package com.example.android_practice_1.push_notifications.service

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.android_practice_1.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

//    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
//        Log.d("check_FCM_response","FCM response")
//        Log.d("check_msg","${message}")
//        Log.d("check_data","${message.data}")
//        Log.d("check_notification","${message.notification}")
//        getFirebaseMessage(message.notification?.title, message.notification?.body)
//    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("check_FCM_response", "FCM response")
        Log.d("check_msg", "$message")
        Log.d("check_data", "${message.data}")
        var title = "title"
        var body = "body of notification"

        getFirebaseMessage(title, body)
    }


    private fun getFirebaseMessage(title: String?, body: String?) {
        val builder = NotificationCompat.Builder(this,"firebase_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(101,builder.build())

    }


}