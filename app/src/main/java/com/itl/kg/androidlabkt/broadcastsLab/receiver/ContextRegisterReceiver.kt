package com.itl.kg.androidlabkt.broadcastsLab.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 *  使用ContextRegister的Demo
 */

class ContextRegisterReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "ContextRegisterReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
        Toast.makeText(context, "${intent.action}", Toast.LENGTH_SHORT).show()
    }

}