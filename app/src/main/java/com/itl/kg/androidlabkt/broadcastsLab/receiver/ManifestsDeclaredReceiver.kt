package com.itl.kg.androidlabkt.broadcastsLab.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 *  在Android 7(API 24)以上，Manifests指定的Broadcasts有相關限制
 *
 *  參考資料：https://developer.android.com/guide/components/broadcast-exceptions
 */

class ManifestsDeclaredReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "ManifestsDeclaredReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
        StringBuffer().apply {
            append("Action: ${intent.action}")
            toString().also { log ->
                Log.d(TAG, log)
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
        }
    }

}