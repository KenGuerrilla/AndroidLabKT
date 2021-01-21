package com.itl.kg.androidlabkt.serviceLab

import android.content.*
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.databinding.FragmentServiceLabBinding
import com.itl.kg.androidlabkt.serviceLab.service.*

/**
 *
 * ServiceLabFragment - Service簡易使用與操作Demo
 *
 * Demo需要搭配Log來觀察相關回應
 *
 * !!! 如果需要Service與UI間的通訊與互動，可參考Broadcast方案或簡易的Messenger方案 !!!
 *
 * 參考資料：https://developer.android.com/guide/components/services
 */

class ServiceLabFragment : Fragment() {

    private var _binding: FragmentServiceLabBinding? = null
    private val binding get() = _binding!!

    private var isBoundServiceActivated = false

    private var mServiceMessenger: Messenger? = null
    private var isMessengerBound: Boolean = false


    companion object {
        private const val TAG = "ServiceLabFragment"
        const val DEVICE_TARGET = 26
    }

    /**
     *  ServiceConnection實作兩個方法，onServiceConnected 與 onServiceDisconnected
     *
     *  a. onServiceConnected是在Service綁定成功之後就會被呼叫。
     *
     *  b. onServiceDisconnected比較不一樣的是他是用來表示Service端遇到預期外的錯誤導致關閉時的通知
     *     簡單來說當Service在運作時被系統回收或者發生Crash時就會呼叫這個方法。
     *     !!! 所以正常程序執行Unbind的動作並不會觸發這個方法，這點要特別注意。 !!!
     *
     *  參考資料： https://developer.android.com/reference/android/content/ServiceConnection
     */

    private val mBoundConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
//            val binder = service as BoundService.LabBinder
//            mBoundService = binder.getService()
            isBoundServiceActivated = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBoundServiceActivated = false
        }
    }

    private val mMessengerConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            mServiceMessenger = Messenger(service)
            isMessengerBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mServiceMessenger = null
            isMessengerBound = false
        }
    }

    // ====================================

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentServiceLabBinding.inflate(inflater, container, false)
        initClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClickListener() {
        binding.mStartForegroundServiceBt.setOnClickListener {
            startForegroundService()
        }

        binding.mStopForegroundServiceBtn.setOnClickListener {
            stopForegroundService()
        }

        binding.mBindBackgroundServiceBtn.setOnClickListener {
            startBindService()
        }

        binding.mUnbindBackgroundServiceBtn.setOnClickListener {
            stopBindService()
        }

        binding.mBindMessengerServiceBtn.setOnClickListener {
            bindMessengerService()
        }

        binding.mSendStartRecordBtn.setOnClickListener {
            sendStartRecordTime()
        }

        binding.mSendStopRecordBtn.setOnClickListener {
            sendStopRecordTime()
        }

        binding.mUnbindMessengerServiceBtn.setOnClickListener {
            unbindMessengerService()
        }

        binding.mCheckDeviceSdkBtn.setOnClickListener {
            showDeviceApiLevel()
        }

    }

    // ====================================

    private fun startForegroundService() {
        Intent(requireContext(), ForegroundService::class.java).also {
            requireActivity().startService(it)
        }
    }

    private fun stopForegroundService() {
        Intent(requireContext(), ForegroundService::class.java).also {
            requireActivity().stopService(it)
        }
    }

    // ====================================

    private fun startBindService() {
        if (!isBoundServiceActivated) {
            Intent(requireActivity(), BoundService::class.java).also { intent ->
                requireActivity().bindService(intent, mBoundConnection, Context.BIND_AUTO_CREATE)
            }
        } else {
            showMessage("BoundService is activated")
        }
    }

    private fun stopBindService() {
        if (isBoundServiceActivated) {
            isBoundServiceActivated = false
            requireActivity().unbindService(mBoundConnection)
        } else {
            showMessage("BoundService is not activate")
        }
    }

    // ====================================

    private fun bindMessengerService() {
        if (!isMessengerBound) {
            Intent(requireActivity(), MessengerService::class.java).also { intent ->
                requireActivity().bindService(intent, mMessengerConnection, Context.BIND_AUTO_CREATE)
            }
        } else {
            showMessage("MessengerService is activated")
        }
    }

    private fun sendStartRecordTime() {
        if (isMessengerBound) {
            val msg = Message.obtain(null, SERVICE_LAB_START_RECORD)
            try {
                mServiceMessenger?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        } else {
            showMessage("MessengerService is not activate")
        }
    }

    private fun sendStopRecordTime() {
        if (isMessengerBound) {
            val msg = Message.obtain(null, SERVICE_LAB_STOP_RECORD)
            try {
                mServiceMessenger?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        } else {
            showMessage("MessengerService is not activate")
        }
    }

    private fun unbindMessengerService() {
        if (isMessengerBound) {
            isMessengerBound = false
            requireActivity().unbindService(mMessengerConnection)
        } else {
            showMessage("MessengerService is not activate")
        }
    }

    // ====================================

    private fun showDeviceApiLevel() {
        showMessage("Device: ${Build.VERSION.SDK_INT} / Target: $DEVICE_TARGET")
    }

    private fun checkApiLevel(): Boolean {
        return Build.VERSION.SDK_INT >= DEVICE_TARGET
    }

    private fun showApiLevelWarringDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.also {
            it.setTitle("API Level Doesn't Support this demo")
            it.setMessage("Device: ${Build.VERSION.SDK_INT} / Target: $DEVICE_TARGET")
            it.setPositiveButton("OK") { dialogInterface, _ -> dialogInterface.dismiss() }
            it.show()
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}