package com.itl.kg.androidlabkt.broadcastsLab

import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.itl.kg.androidlabkt.broadcastsLab.receiver.ContextRegisterReceiver
import com.itl.kg.androidlabkt.broadcastsLab.receiver.OnCountingDoneReceiver
import com.itl.kg.androidlabkt.broadcastsLab.receiver.OnCountingDoneReceiver.Companion.MY_ACTION_COUNTING
import com.itl.kg.androidlabkt.broadcastsLab.receiver.OnCountingDoneReceiver.Companion.MY_ACTION_COUNTING_DONE
import com.itl.kg.androidlabkt.broadcastsLab.receiver.OnCountingDoneReceiverListener
import com.itl.kg.androidlabkt.broadcastsLab.receiver.StartCountingDownReceiver.Companion.ACTION_START_COUNTDOWN
import com.itl.kg.androidlabkt.broadcastsLab.receiver.PendingBroadcastReceiver
import com.itl.kg.androidlabkt.databinding.FragmentBroadcastsLabBinding

/**
 *
 *  !!! 注意事項 !!!
 *  有Register就要記得Unregister，否則造成記憶體不足的問題。
 *  例如onCreate()時Register，要記得在onDestroy()時Unregister，在onResume()就要在onPause()
 *
 *
 *  Demo有三個
 *  a. 接收系統廣播，這邊實作接收系統開關飛航模式時所發送的廣播
 *  b. 實作當BroadcastReceiver需要處理稍微耗時的工作可參考的方案
 *  c. 實作自定義的廣播，透過Fragment發送START廣播給Service，Service再將倒數的情況廣播給Fragment顯示於UI
 *
 *  !!! 備註 !!!
 *  --- 這個範例為了方便，把廣播ID常數定義在各個物件的伴生物件裡頭，其實可以另外規劃處理 (我的想法) ---
 *
 *  相關資訊可參考官方文件：https://developer.android.com/guide/components/broadcasts
 *
 */

class BroadcastsLabFragment : Fragment() {

    companion object {
        private const val TAG = "BroadcastsLabFragment"
    }

    private var _binding: FragmentBroadcastsLabBinding? = null
    private val binding get() = _binding!!

    private var broadcastReceiver: ContextRegisterReceiver? = null
    private var pendingReceiver: PendingBroadcastReceiver? = null
    private var onCountingDownReceiver: OnCountingDoneReceiver? = null

    private lateinit var countingDownServiceConnection: ServiceConnection
    private var isCountingDownServiceBound: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBroadcastsLabBinding.inflate(inflater, container, false)

        initListener()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadcastReceiver()
        unregisterPendingReceiver()
        unbindCountingDownService()
    }

    private fun initListener() {
        binding.mRegisterBroadcastBtn.setOnClickListener {
            registerBroadcastReceiver()
        }

        binding.mUnregisterBroadcastBtn.setOnClickListener {
            unregisterBroadcastReceiver()
        }

        binding.mRegisterPendingReceiverBtn.setOnClickListener {
            registerPendingReceiver()
        }

        binding.mUnregisterPendingReceiverBtn.setOnClickListener {
            unregisterPendingReceiver()
        }

        binding.mBindCustomReceiverServiceBtn.setOnClickListener {
            bindCountingDownService()
        }

        binding.mSendCustomBroadcastBtn.setOnClickListener {
            sendStartCountingDownBroadcast()
        }

        binding.mUnbindCustomReceiverServiceBtn.setOnClickListener {
            unbindCountingDownService()
        }

    }

    // ==================== System Broadcast Receiver ====================

    private fun registerBroadcastReceiver() {
        if (broadcastReceiver == null) {
            broadcastReceiver = ContextRegisterReceiver()
            val filter = IntentFilter().apply {
                // 增加需要攔截的訊息，下面的意思是當飛航模式的狀態被改變時
                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            }
            // Normal Broadcast可接收系統中所有的廣播
            requireActivity().registerReceiver(broadcastReceiver, filter)
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

    // ==================== Pending Receiver ====================

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


    // ==================== Counting Down Broadcast ====================

    private fun bindCountingDownService() {
        if (!isCountingDownServiceBound) {
            countingDownServiceConnection = getCustomServiceConnection()
            Intent(requireActivity(), CountingDownService::class.java).also { intent ->
                requireActivity().bindService(intent, countingDownServiceConnection, Context.BIND_AUTO_CREATE)
            }
            registerCountingDownReceiver()
        } else {
            showMessage("CountingDownService is activated")
        }
    }

    private fun registerCountingDownReceiver() {
        if (onCountingDownReceiver == null) {
            onCountingDownReceiver = OnCountingDoneReceiver(getOnCountingDownListener())
            val filter = IntentFilter().apply {
                // 很重要，不要忘了在Filter裡頭加Action！！否則Receiver收不到廣播
                addAction(MY_ACTION_COUNTING)
                addAction(MY_ACTION_COUNTING_DONE)
            }
            LocalBroadcastManager.getInstance(requireActivity())
                .registerReceiver(onCountingDownReceiver as BroadcastReceiver, filter)
        }
    }

    private fun sendStartCountingDownBroadcast() {
        Intent().also { intent ->
            intent.action = ACTION_START_COUNTDOWN
//            intent.putExtra("data", "Notice me senpai!")
            LocalBroadcastManager.getInstance(requireActivity()).sendBroadcast(intent)
        }
    }

    private fun unbindCountingDownService() {
        if (isCountingDownServiceBound) {
            requireActivity().unbindService(countingDownServiceConnection)

            LocalBroadcastManager.getInstance(requireActivity())
                .unregisterReceiver(onCountingDownReceiver as BroadcastReceiver)

        } else {
            showMessage("CountingDownService is not activate")
        }
    }

    private fun getOnCountingDownListener(): OnCountingDoneReceiverListener =
        object : OnCountingDoneReceiverListener {
            override fun onCounting(msg: String) {
                binding.mSendCustomBroadcastBtn.text = msg
            }

            override fun onDone() {
                binding.mSendCustomBroadcastBtn.text = "Send Broadcast to Receiver"
            }
        }

    // ==================== Service connection ====================

    private fun getCustomServiceConnection(): ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            isCountingDownServiceBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isCountingDownServiceBound = false
        }
    }

    // ==================== Tools ====================

    private fun showMessage(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

}