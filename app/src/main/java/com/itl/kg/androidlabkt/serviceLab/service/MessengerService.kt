package com.itl.kg.androidlabkt.serviceLab.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.itl.kg.androidlabkt.TimeCalculateTool
import kotlinx.coroutines.*
import java.util.*

/**
 *  透過Handler的方式來啟動或停止計數
 *
 *  a. 這邊依照官方範例實作單向控制，如果要雙向就必須在ServiceLabFragment也實作一個Messenger
 */

const val SERVICE_LAB_START_RECORD = 1
const val SERVICE_LAB_STOP_RECORD = 0

class MessengerService : Service(), RecordTimeInterface {

    companion object {
        const val TAG = "MessengerService"
    }

    private lateinit var mMessenger: Messenger

    private var job: Job? = null

    private val timeTools = TimeCalculateTool()

    private lateinit var startDate: Date

    override fun startRecordTime() {
        Log.d(TAG, "Start Record Time")

        // 如果job不是null則代表已經有執行緒正在處理
        if (job == null) {
            startCountingDemo()
        }
    }

    private fun startCountingDemo() {
        val scope = CoroutineScope(Dispatchers.IO)
        job = scope.launch {
            startDate = Date()
            while (true){
                Log.d(
                    TAG,
                    "Start: ${timeTools.getTimeString(startDate)} " +
                            "/ Now: ${timeTools.getTimeString(Date())} " +
                            "/ Diff: ${timeTools.getTimeDifference(startDate, Date())}"
                )
                delay(1000)
            }
        }
    }

    override fun stopRecordTime() {
        Log.d(TAG, "Stop Record Time")
        job?.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        mMessenger = Messenger(MessengerIncomingHandler(this))
        return mMessenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")
        stopRecordTime()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }


    // 由於internal為一個獨立的class，因此透過RecordTimeInterface來回應Handler接收到的訊息
    internal class MessengerIncomingHandler(
        private val callbackHandler: RecordTimeInterface
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                SERVICE_LAB_START_RECORD -> callbackHandler.startRecordTime()
                SERVICE_LAB_STOP_RECORD -> callbackHandler.stopRecordTime()
            }
        }
    }

}

interface RecordTimeInterface {
    fun startRecordTime()
    fun stopRecordTime()
}



