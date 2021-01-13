package com.itl.kg.androidlabkt.broadcastsLab

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.itl.kg.androidlabkt.broadcastsLab.receiver.OnCountingDoneReceiver.Companion.MY_ACTION_COUNTING
import com.itl.kg.androidlabkt.broadcastsLab.receiver.OnCountingDoneReceiver.Companion.MY_ACTION_COUNTING_DONE
import com.itl.kg.androidlabkt.broadcastsLab.receiver.StartCountingDownReceiver
import com.itl.kg.androidlabkt.broadcastsLab.receiver.StartCountingDownReceiver.Companion.ACTION_START_COUNTDOWN
import com.itl.kg.androidlabkt.broadcastsLab.receiver.StartCountingDownListener
import kotlinx.coroutines.*

/**
 *  這個Service實作倒數的功能，由Fragment發送Broadcast來啟動倒數
 *  再透過另外的Broadcast來回傳倒數的情況
 */

class CountingDownService : Service() {

    companion object {
        private const val TAG = "BroadcastReceiverService"
    }

    private val binder: BroadcastLabBinder = BroadcastLabBinder()

    private var customBroadcastStartCountingDownReceiver: StartCountingDownReceiver? = null

    private var job: Job? = null

    private val localBroadcastManager =  LocalBroadcastManager.getInstance(this)

    override fun onBind(intent: Intent): IBinder? {
        registerCustomReceiver()
        Log.d(TAG, "onBind")
        return binder
    }

    private fun registerCustomReceiver() {
        if (customBroadcastStartCountingDownReceiver == null) {
            Log.d(TAG, "registerCustomReceiver")
            customBroadcastStartCountingDownReceiver = StartCountingDownReceiver(getReceiverCallback())
            val filter = IntentFilter().apply {
                addAction(ACTION_START_COUNTDOWN)
            }
            LocalBroadcastManager.getInstance(this).registerReceiver(customBroadcastStartCountingDownReceiver as BroadcastReceiver, filter)
        }
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "onUnbind")
        if (customBroadcastStartCountingDownReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(customBroadcastStartCountingDownReceiver as BroadcastReceiver)
            customBroadcastStartCountingDownReceiver = null
        }
        return super.onUnbind(intent)
    }


    private fun getReceiverCallback(): StartCountingDownListener = object : StartCountingDownListener {
        override fun startCounting() {
            if (job == null) {
                val scope = CoroutineScope(Dispatchers.IO)
                job = scope.launch {
                    for (x in 0..5) {
                        sendCountingDownBroadcast(x.toString())
                        delay(1000)
                    }
                    sendCountingDownBroadcast("Done!!")
                    delay(2000)
                    sendDoneBroadcast()
                    job = null
                }
            }
        }
    }

    private fun sendCountingDownBroadcast(msg: String) {
        Intent().also { intent ->
            intent.action = MY_ACTION_COUNTING
            intent.putExtra("msg", msg)
            localBroadcastManager.sendBroadcast(intent)
        }
    }

    private fun sendDoneBroadcast() {
        Intent().also { intent ->
            intent.action = MY_ACTION_COUNTING_DONE
            localBroadcastManager.sendBroadcast(intent)
        }
    }

    inner class BroadcastLabBinder : Binder() {
        fun getService() : CountingDownService = this@CountingDownService
    }
}