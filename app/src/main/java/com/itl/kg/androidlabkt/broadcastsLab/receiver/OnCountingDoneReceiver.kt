package com.itl.kg.androidlabkt.broadcastsLab.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


/**
 *  這個Receiver要掛在Fragment
 */

class OnCountingDoneReceiver(
    private val listener: OnCountingDoneReceiverListener
) : BroadcastReceiver() {

    companion object {
        private const val TAG = "OnDoneBroadcastReceiver"
        const val MY_ACTION_COUNTING = "com.itl.kg.ACTION_COUNTING"
        const val MY_ACTION_COUNTING_DONE = "com.itl.kg.ACTION_COUNTING_DONE"
        const val INTENT_ARG_MSG = "msg"
    }


    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive ${intent.action}")
        val msg = intent.extras?.getString(INTENT_ARG_MSG, "Message was empty") ?: "Message was null"
        when(intent.action) {
            MY_ACTION_COUNTING -> listener.onCounting(msg)
            MY_ACTION_COUNTING_DONE -> listener.onDone()
        }
    }

}

interface OnCountingDoneReceiverListener {
    fun onCounting(msg: String)
    fun onDone()
}