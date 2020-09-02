package com.itl.kg.androidlabkt.biometricsLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.biometricsLab.mvvm.BioLabFragmentViewModel
import com.itl.kg.androidlabkt.biometricsLab.mvvm.BioLabFragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_biometrics_lab.*

/**
 *
 * BiometricsLab - 使用BiometricsTools搭配MVVM進行簡單的Demo
 *
 *  使用BiometricsTools將BiometricPrompt封裝，並簡化其設定介面
 *  針對ErrorCode的部分另外提供SimpleBioErrorCodeParser作為基礎的ErrorCodeParser
 *
 */

class BiometricsLabFragment : Fragment() {

    private val viewModel by viewModels<BioLabFragmentViewModel> {
        BioLabFragmentViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_biometrics_lab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLiveDate()

        mStartBiometricsBtn.setOnClickListener {
            viewModel.startBioAuth(requireActivity(),
                "這是Title",
                "這是SubTitle",
                "這是Neg按鈕")
        }
    }

    override fun onResume() {
        super.onResume()
        initCheckSupportStatus()
    }

    private fun initCheckSupportStatus() {

        val status = viewModel.checkDeviceSupport(requireContext())
        mBioStatusTv.text = viewModel.parserStatusErrorCode(requireContext(), status)

    }

    private fun initLiveDate() {
        viewModel.getAuthResultLiveData().observe(viewLifecycleOwner, Observer {

            val authResult = viewModel.parserAuthErrorCode(requireContext(), it)
            mBioStatusTv.text = authResult

        })
    }

}