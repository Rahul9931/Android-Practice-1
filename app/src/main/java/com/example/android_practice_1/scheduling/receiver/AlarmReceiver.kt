package com.example.android_practice_1.scheduling.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.android_practice_1.R
import com.example.android_practice_1.scheduling.activity.TwoActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "alarm_channel"

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,"Alarm Notification", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, TwoActivity::class.java)
        val pendingIntent = PendingIntent.getActivities(context,0,
            arrayOf(notificationIntent),PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Alarm Alert")
            .setContentText("This is your Alarm Notification")
            .setSmallIcon(R.drawable.notification_active)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(0,notification)
    }


}