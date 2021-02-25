package com.itl.kg.androidlabkt.nevigationLab.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.itl.kg.androidlabkt.databinding.FragmentNavLabRegisterCBinding
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavRegisterViewModelFactory
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavRegisterViewModel

/**
 *
 *  NavLabRegisterCFragment - 註冊頁面第三頁
 *
 *  將使用者輸入的帳號密碼透過viewModel進行註冊
 *
 */

class NavLabRegisterCFragment : Fragment() {

    companion object {
        const val TAG = "NavLabRegisterCFragment"
    }

    private val viewModel: NavRegisterViewModel by activityViewModels {
        NavRegisterViewModelFactory()
    }

    private var _binding: FragmentNavLabRegisterCBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNavLabRegisterCBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {

        binding.mNavLabRegisterThirdBtn.setOnClickListener {
            // 儲存帳號密碼
            if (checkInput()) {
                setAccountInfo()
                it.findNavController().navigate(NavLabRegisterCFragmentDirections.actionNavLabRegisterCFragmentToNavLabRegisterDFragment())
            }
        }
    }

    private fun setAccountInfo() {
        val account = binding.mNavLabRegisterAccountEt.text.toString()
        val password = binding.mNavLabRegisterPasswordEt.text.toString()
        viewModel.setRegisterAccount(account, password)
    }

    private fun checkInput(): Boolean {
        return binding.mNavLabRegisterAccountEt.text.toString().isNotBlank() &&
                binding.mNavLabRegisterPasswordEt.text.toString().isNotBlank()
    }

}