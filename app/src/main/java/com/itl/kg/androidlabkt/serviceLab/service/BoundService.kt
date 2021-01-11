package com.itl.kg.androidlabkt.serviceLab.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.itl.kg.androidlabkt.TimeCalculateTool
import kotlinx.coroutines.*
import java.util.*

/**
 *  綁定Service後每秒顯示 (綁定時間) / 現在時間 / 時間差距 (時：分：秒) 於Log當中
 */


class BoundService : Service() {

    companion object {
        const val TAG = "BoundService"
    }

    private val binder = LabBinder()

    var job: Job? = null
    lateinit var startDate: Date

    private val timeTools = TimeCalculateTool()

    override fun onBind(p0: Intent?): IBinder? {
        startDate = Date()
        startRecodeShowInLog()
        Log.d(TAG, "onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stopRecodeShowInLog()
        Log.d(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    private fun startRecodeShowInLog() {
        val scope = CoroutineScope(Dispatchers.IO)
        job = scope.launch {
            while (true) {
                Log.d(TAG,
                    "Start: ${timeTools.getTimeString(startDate)} " +
                            "/ Now: ${timeTools.getTimeString(Date())} " +
                            "/ Diff: ${timeTools.getTimeDifference(startDate, Date())}"
                )
                delay(1000)
            }
        }
    }

    private fun stopRecodeShowInLog() {
        Log.d(TAG, "Coroutine Cancel")
        job?.cancel()
    }


    inner class LabBinder : Binder() {
        fun getService() : BoundService = this@BoundService
    }
}
