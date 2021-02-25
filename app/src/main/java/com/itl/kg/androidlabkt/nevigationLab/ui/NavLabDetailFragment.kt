package com.itl.kg.androidlabkt.nevigationLab.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.itl.kg.androidlabkt.databinding.FragmentNavLabDetailBinding
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavLabMainViewModelFactory
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavLabMainViewModel

/**
 *
 *  NavLabDetailFragment - 為登入成功後顯示的畫面
 *
 *  實做一個Logout功能清空登入狀態與帳號資訊，並回到NavLabMainFragment
 *
 *  使用者離開貨登出後無法使用返回鍵回到一頁
 *
 */

class NavLabDetailFragment : Fragment() {

    val viewModel: NavLabMainViewModel by activityViewModels {
        NavLabMainViewModelFactory()
    }

    private var _binding: FragmentNavLabDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavLabDetailBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.mNavLabLogoutBtn.setOnClickListener {
            viewModel.logout(requireContext())
            Toast.makeText(requireContext(), "Logout", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

}