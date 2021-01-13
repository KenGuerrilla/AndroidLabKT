package com.itl.kg.androidlabkt.broadcastsLab.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


/**
 *  這個Receiver掛載Service上頭
 */

class StartCountingDownReceiver(
    private val listener: StartCountingDownListener
) : BroadcastReceiver() {

    companion object {
        private const val TAG = "CustomBroadcastsCountingReceiver"
        const val ACTION_START_COUNTDOWN = "com.itl.kg.ACTION_START_COUNTDOWN"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive ${intent.action}")
        when (intent.action) {
            ACTION_START_COUNTDOWN -> listener.startCounting()
        }
    }

}

interface StartCountingDownListener {
    fun startCounting()
}