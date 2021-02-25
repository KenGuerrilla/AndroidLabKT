package com.itl.kg.androidlabkt.nevigationLab.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.itl.kg.androidlabkt.databinding.FragmentNavLabMainBinding
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavLabMainViewModelFactory
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavLabMainViewModel
import com.itl.kg.androidlabkt.nevigationLab.ui.login.NavLabLoginActivity

class NavLabMainFragment : Fragment() {

    companion object {
        const val TAG = "NavLabMainFragment"
    }

    private val viewModel: NavLabMainViewModel by viewModels {
        NavLabMainViewModelFactory()
    }

    private var _binding: FragmentNavLabMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavLabMainBinding.inflate(inflater, container, false)
        initListener()

        return binding.root
    }

    private fun initListener() {
        // 透過Activity的ViewModel做登入檢查，如果還沒登入則進到登入頁面，反之進入Detail畫面
        binding.mDetailInfoBtn.setOnClickListener {
            checkLoginStatus()
        }
    }

    private fun checkLoginStatus() {
        val isLogin = viewModel.isLogin(requireContext())
        if (isLogin) {
            findNavController().navigate(NavLabMainFragmentDirections.actionNavLabMainFragmentToNavLabDetailFragment())
        } else {
            // 設定ActivityResult
            initActivityResultListener()
        }
    }

    // 取得來自LoginActivity的ActivityResult
    private var resultLauncher
            = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 登入成功直接進Detail
            findNavController().navigate(NavLabMainFragmentDirections.actionNavLabMainFragmentToNavLabDetailFragment())
        }
    }

    private fun initActivityResultListener() {
        val intent = Intent(requireContext(), NavLabLoginActivity::class.java)
        resultLauncher.launch(intent)
    }
}