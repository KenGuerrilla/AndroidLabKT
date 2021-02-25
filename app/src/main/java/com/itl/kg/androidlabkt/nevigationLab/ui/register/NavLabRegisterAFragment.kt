package com.itl.kg.androidlabkt.nevigationLab.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.itl.kg.androidlabkt.databinding.FragmentNavLabRegisterABinding

class NavLabRegisterAFragment : Fragment() {

    private var _binding: FragmentNavLabRegisterABinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNavLabRegisterABinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }


    private fun initListener() {
        binding.mLabRegisterFirestBtn.setOnClickListener {
            it.findNavController().navigate(NavLabRegisterAFragmentDirections.actionNavLabRegisterAFragmentToNavLabRegisterBFragment())
        }
    }


    // 原先用於接收最後一個註冊頁面的完成通知，接收通知後即關閉Activity回到Login頁面
    private fun initFragmentResultListener() {
        setFragmentResultListener("200") { requestKey: String, bundle: Bundle ->
            if (requestKey == "200") {
                val result = bundle.getBoolean("result")
                if (result) requireActivity().finish()
            }
        }
    }

}