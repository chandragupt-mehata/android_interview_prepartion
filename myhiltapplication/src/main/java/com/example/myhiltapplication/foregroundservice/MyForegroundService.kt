package com.example.myhiltapplication.foregroundservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.myhiltapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A deadlock is a situation where a thread T, holds a key needed by a thread T2,
 * and T2 holds the key needed by T,
 */
class MyForegroundService: Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        println("service is inside on create")
    }

    private var counter = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("service is inside on onStartCommand, : ${intent?.action}")
        coroutineScope.launch {
            while (true) {
                counter ++
                delay(1000)
                println("service is running, $counter")
                if (counter > 10) {
                    stopSelf()
                    cancel()
                }
            }
        }
        val CHANNELID = "Foreground Service ID"
        val channel = NotificationChannel(
            CHANNELID,
            CHANNELID,
            NotificationManager.IMPORTANCE_LOW
        )

        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification: Notification.Builder = Notification.Builder(this, CHANNELID)
            .setContentText("Service is running")
            .setContentTitle("Service enabled")
            .setSmallIcon(R.drawable.ic_launcher_background)

        startForeground(1001, notification.build())
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        println("service is inside on onBind, : ${p0?.action}")
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        println("service is inside on onDestroy")
    }
}