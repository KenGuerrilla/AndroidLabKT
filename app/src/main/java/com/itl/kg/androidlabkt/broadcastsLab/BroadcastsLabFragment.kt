package com.itl.kg.androidlabkt.broadcastsLab

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.broadcastsLab.receiver.ContextRegisterReceiver
import com.itl.kg.androidlabkt.broadcastsLab.receiver.PendingBroadcastReceiver

/**
 *
 *  !!! 注意事項 !!!
 *  有Register就要記得Unregister，否則造成記憶體不足的問題。
 *  例如onCreate()時Register，要記得在onDestroy()時Unregister，在onResume()就要在onPause()
 *
 *  相關資訊可參考官方文件：https://developer.android.com/guide/components/broadcasts
 *
 */

class BroadcastsLabFragment : Fragment() {

    companion object {
        private const val TAG = "BroadcastsLabFragment"
    }

    private lateinit var btRegisterBroadcast: Button
    private lateinit var btUnregisterBroadcast: Button

    private lateinit var btRegisterPendingReceiver: Button
    private lateinit var btUnregisterPendingReceiver: Button

    private var broadcastReceiver: ContextRegisterReceiver? = null
    private var pendingReceiver: PendingBroadcastReceiver? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_broadcasts_lab, container, false)
        initView(view)
        initListener()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadcastReceiver()
    }

    private fun initView(view: View) {
        btRegisterBroadcast = view.findViewById(R.id.mRegisterBroadcastBtn)
        btUnregisterBroadcast = view.findViewById(R.id.mUnregisterBroadcastBtn)

        btRegisterPendingReceiver = view.findViewById(R.id.mRegisterPendingReceiverBtn)
        btUnregisterPendingReceiver = view.findViewById(R.id.mUnregisterPendingReceiverBtn)
    }

    private fun initListener() {
        btRegisterBroadcast.setOnClickListener {
            registerBroadcastReceiver()
        }

        btUnregisterBroadcast.setOnClickListener {
            unregisterBroadcastReceiver()
        }

        btRegisterPendingReceiver.setOnClickListener {
            Log.d(TAG, "Clicked!!")
            registerPendingReceiver()
        }

        btUnregisterPendingReceiver.setOnClickListener {
            unregisterPendingReceiver()
        }

    }


    private fun registerBroadcastReceiver() {
        if (broadcastReceiver == null) {
            broadcastReceiver = ContextRegisterReceiver()
            val filter = IntentFilter().apply {
                // 增加需要攔截的訊息，下面的意思是當飛航模式被開啟或關閉時
                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            }
            // 這個是裝置廣播接收
            requireActivity().registerReceiver(broadcastReceiver, filter)
            // TODO 目前無法接收訊息
//            LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(broadcastReceiver, filter)
        } else {
            Toast.makeText(requireActivity(), "BroadcastReceiver was registered!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun unregisterBroadcastReceiver() {
        if (broadcastReceiver != null) {
            requireActivity().unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
        } else {
            Toast.makeText(requireActivity(), "BroadcastReceiver was unregister!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerPendingReceiver() {
        if (pendingReceiver == null) {
            pendingReceiver = PendingBroadcastReceiver()
            val filter = IntentFilter().apply {
                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            }
            requireActivity().registerReceiver(pendingReceiver, filter)
        } else {
            Toast.makeText(requireActivity(), "PendingReceiver was registered!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun unregisterPendingReceiver() {
        if (pendingReceiver != null) {
            requireActivity().unregisterReceiver(pendingReceiver)
            pendingReceiver = null
        } else {
            Toast.makeText(requireActivity(), "PendingReceiver was unregister!", Toast.LENGTH_SHORT).show()
        }
    }


}