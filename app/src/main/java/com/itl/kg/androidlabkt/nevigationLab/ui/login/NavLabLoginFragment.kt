package com.itl.kg.androidlabkt.nevigationLab.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.itl.kg.androidlabkt.databinding.FragmentNavLabLoginBinding
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavLabLoginViewModelFactory
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavLabLoginViewModel
import com.itl.kg.androidlabkt.nevigationLab.repository.LoginResult


class NavLabLoginFragment : Fragment() {

    private val viewModel: NavLabLoginViewModel by activityViewModels {
        NavLabLoginViewModelFactory()
    }

    private var _binding: FragmentNavLabLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavLabLoginBinding.inflate(inflater, container, false)

        initListener()
        initLiveData()

        return binding.root
    }

    private fun initLiveData() {
        viewModel.accountLiveData.observe(viewLifecycleOwner, Observer {
            binding.mNavLabAccountEt.apply {
                this.setText(it)
            }
        })

        viewModel.loginResultLiveData.observe(viewLifecycleOwner, Observer {
            checkLoginResult(it)
        })
    }

    private fun initListener() {

        // 登入
        binding.mNavLabLoginBtn.setOnClickListener {
            loginToDetail()
        }

        // 進入註冊頁面
        binding.mNavLabRegisterBtn.setOnClickListener {
            findNavController().navigate(NavLabLoginFragmentDirections.actionNavLabLoginFragmentToNavLabRegisterActivity())
            it.isClickable = false
        }
    }

    // 檢查LiveData傳來的登入結果
    private fun checkLoginResult(it: LoginResult) {
        if (it.result == LoginResult.RESULT_OK) {
            viewModel.saveLoginStatus(true, requireContext())
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            // 登入成功回傳FragmentResult OK的訊息
            viewModel.loginSuccess()
            // 登入成功則關閉登入頁面
            requireActivity().finish()
        } else {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginToDetail() {
        val account = binding.mNavLabAccountEt.text.toString()
        val password = binding.mNavLabPasswordEt.text.toString()
        viewModel.login(account, password)
    }

    override fun onResume() {
        super.onResume()
        getLastLoginInfo()
        releaseButton()
    }

    private fun getLastLoginInfo() {
        viewModel.getLastLoginAccount(requireContext())
    }

    private fun releaseButton() {
        binding.mNavLabRegisterBtn.isClickable = true
    }

}