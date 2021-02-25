package com.itl.kg.androidlabkt.nevigationLab.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.itl.kg.androidlabkt.databinding.FragmentNavLabRegisterDBinding
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavRegisterViewModelFactory
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavRegisterViewModel

/**
 *
 *  NavLabRegisterDFragment - 註冊頁面第四頁
 *
 *  註冊畫面最後確認頁，使用者點下Finish才算註冊完成
 *
 *  註冊完成後關閉NavLabRegisterActivity回到登入畫面
 *
 */

class NavLabRegisterDFragment : Fragment() {

    private val viewModel: NavRegisterViewModel by activityViewModels {
        NavRegisterViewModelFactory()
    }

    private var _binding: FragmentNavLabRegisterDBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNavLabRegisterDBinding.inflate(inflater, container, false)

        initListener()

//        initFragmentResult()

        return binding.root
    }

    private fun initListener() {
        binding.mLabRegisterFourthBtn.setOnClickListener {
            // 儲存註冊資訊
            viewModel.saveAccountInfo(requireContext())

            // 完成註冊流程，直接關閉Activity
            requireActivity().finish()
        }
    }

    // 當畫面帶到這個Fragment就代表註冊完成，所以要送一個完成訊息給第一頁
    // 未來Toolbar的返回鍵可能會需要
    private fun initFragmentResult() {
        val bundle = Bundle().apply {
            putBoolean("result", true)
        }
        setFragmentResult("200", bundle)
    }

}