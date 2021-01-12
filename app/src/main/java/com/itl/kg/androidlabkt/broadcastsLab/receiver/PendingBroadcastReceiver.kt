package com.itl.kg.androidlabkt.broadcastsLab.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 *  當onReceive()被呼叫後，BroadcastReceiver隨時都有可能被系統回收
 *  如果onReceive()當中需要做一些(稍微)耗時的工作，那就可以考慮goAsync()的方案
 *
 *  相對耗時的工作，則可以參考JobService與JobScheduler這兩個關鍵字
 *
 *  下列程式碼源自官方文件
 */

class PendingBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "PendingBroadcastReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
        val pendingResult: PendingResult = goAsync()
        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            for (x in 0..10) {
                Log.d(TAG, "Counter: $x")
                delay(1000)
            }
            // Must call finish() so the BroadcastReceiver can be recycled.
            pendingResult.finish()
        }

        /**
         *  官方文件範例是使用AsyncTask，但是AsyncTask在API Level 30被deprecated
         *  所以這邊更換為Coroutine
         *
         *  資料可參考：https://developer.android.com/reference/android/os/AsyncTask
         */
//        val asyncTask = Task(pendingResult, intent)
//        asyncTask.execute()
    }

    private class Task(
        private val pendingResult: PendingResult,
        private val intent: Intent
    ) : AsyncTask<String, Int, String>() {
        override fun doInBackground(vararg params: String?): String {
            Log.d(TAG, "${intent.action}")
            return toString().also { log ->
                Log.d(TAG, log)
            }
        }

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d(TAG, "onPreExecute")
            // Must call finish() so the BroadcastReceiver can be recycled.
            pendingResult.finish()
        }
    }

}