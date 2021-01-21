package com.itl.kg.androidlabkt.biometricsLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.itl.kg.androidlabkt.biometricsLab.mvvm.BioLabFragmentViewModel
import com.itl.kg.androidlabkt.biometricsLab.mvvm.BioLabFragmentViewModelFactory
import com.itl.kg.androidlabkt.databinding.FragmentBiometricsLabBinding

/**
 *
 * BiometricsLab - 使用BiometricsTools搭配MVVM進行簡單的Demo
 *
 *  使用BiometricsTools將BiometricPrompt封裝，並簡化其設定介面
 *  針對ErrorCode的部分另外提供SimpleBioErrorCodeParser作為基礎的ErrorCodeParser
 *
 */

class BiometricsLabFragment : Fragment() {

    private var _binding: FragmentBiometricsLabBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<BioLabFragmentViewModel> {
        BioLabFragmentViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBiometricsLabBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLiveDate()

        binding.mStartBiometricsBtn.setOnClickListener {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initCheckSupportStatus() {

        val status = viewModel.checkDeviceSupport(requireContext())
        binding.mBioStatusTv.text = viewModel.parserStatusErrorCode(requireContext(), status)

    }

    private fun initLiveDate() {
        viewModel.getAuthResultLiveData().observe(viewLifecycleOwner, Observer {

            val authResult = viewModel.parserAuthErrorCode(requireContext(), it)
            binding.mBioStatusTv.text = authResult

        })
    }

}