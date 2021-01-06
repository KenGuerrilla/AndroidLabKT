package com.itl.kg.androidlabkt.serviceLab

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.serviceLab.mvvm.ServiceLabViewModel
import com.itl.kg.androidlabkt.serviceLab.mvvm.ServiceLabViewModelFactory
import com.itl.kg.androidlabkt.serviceLab.service.ForegroundServiceLab


class ServiceLabFragment : Fragment() {

    private lateinit var btStartForegroundService: Button
    private lateinit var btStopForegroundService: Button
    private lateinit var btShowDeviceSdk: Button

    companion object {
        const val DEVICE_TARGET = 26
    }

    private val viewModel by viewModels<ServiceLabViewModel> {
        ServiceLabViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service_lab, container, false)

        btStartForegroundService = view.findViewById(R.id.mStartForegroundServiceBt)
        btStopForegroundService = view.findViewById(R.id.mStopForegroundServiceBtn)
        btShowDeviceSdk = view.findViewById(R.id.mCheckDeviceSdkBtn)

        initClickListener()

        return view
    }

    private fun initClickListener() {
        btStartForegroundService.setOnClickListener {
            Intent(requireContext(), ForegroundServiceLab::class.java).also {
                requireActivity().startService(it)
            }
        }

        btStopForegroundService.setOnClickListener {
            Intent(requireContext(), ForegroundServiceLab::class.java).also {
                requireActivity().stopService(it)
            }
        }

        btShowDeviceSdk.setOnClickListener {

            if (viewModel.checkDeviceSdk(DEVICE_TARGET)) {
                Toast.makeText(requireActivity(), "Passed!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireActivity(), "Device: ${Build.VERSION.SDK_INT} / Target: $DEVICE_TARGET", Toast.LENGTH_SHORT).show()
            }
        }

        val status = TextUtils.isDigitsOnly("a")

        Log.d("TAG", "status: $status")

    }

}