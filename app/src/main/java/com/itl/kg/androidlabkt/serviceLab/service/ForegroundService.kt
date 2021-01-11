package com.itl.kg.androidlabkt.serviceLab.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.itl.kg.androidlabkt.serviceLab.ServiceLabFragment
import kotlinx.coroutines.*

/**
 *  Foreground Service 於 Android 9(API 28) 需要像系統請求權限，否則會彈出 SecurityException
 *
 *  使用時透過 startForeground(ONGOING_NOTIFICATION_ID, notification) 來啟動
 *
 *  當啟動Service時，可以透過前景Notification來通知使用者背景正在執行Service
 *
 *  參考文件：https://developer.android.com/guide/components/foreground-services
 */

class ForegroundService: Service() {

    companion object {
        const val DEVICE_TARGET = 26
        const val CHANNEL_DEFAULT_IMPORTANCE = "kg.itl"
        const val ONGOING_NOTIFICATION_ID = 64
    }

    private val tag = "ForegroundServiceLab"

    // 用job來判斷是不是有Coroutine在跑
    private var job: Job? = null

    // 不用Bind的方式啟動Service就回傳null
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startNotificationDemo()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "Stop service lab")
    }

    private fun startNotificationDemo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(tag, "Start service lab")

            // Notification初始化
            initNotificationChannel()
            startForeground(ONGOING_NOTIFICATION_ID, getNotification())

            if (job == null) {
                startCounter()
            }

        } else {
            Log.d(tag, "Device sdk lower $DEVICE_TARGET")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotificationChannel() {
        // 必須要指定相同的Channel ID才行，否則會彈出Bad notification for startForeground的錯誤訊息
        val chan = NotificationChannel(
            CHANNEL_DEFAULT_IMPORTANCE,
            "This is my service",
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(chan)
    }

    private fun getPendingIntent(): PendingIntent {
        return Intent(this, ServiceLabFragment::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotification(): Notification {
        return Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle("AndroidLabKT Notification Title")
            .setContentText("This is AndroidLabKT Foreground service desc text!!!!!")
            .setSmallIcon(android.R.drawable.stat_sys_warning)
            .setContentIntent(getPendingIntent())
            .build()
    }

    private fun startCounter() {
        val scope = CoroutineScope(Dispatchers.IO)

        job = scope.launch {
            delay(3000)
            for (x in 0..5) {
                updateNotification("Counter: $x")
                delay(1000)
            }
            updateNotification("Done!!!")
            delay(5000)
            stopSelf()
        }

    }

    /**
     *  更新需要指定相同的ID
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun updateNotification(message: String) {
        coroutineScope {
            val notification = Notification.Builder(this@ForegroundService, CHANNEL_DEFAULT_IMPORTANCE)
                .setContentTitle("AndroidLabKT Notification Title")
                .setContentText(message)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setContentIntent(getPendingIntent())
                .build()

            startForeground(ONGOING_NOTIFICATION_ID, notification)
        }
    }
}