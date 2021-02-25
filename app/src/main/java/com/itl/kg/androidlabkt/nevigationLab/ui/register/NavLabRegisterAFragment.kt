package com.itl.kg.androidlabkt.nevigationLab.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.itl.kg.androidlabkt.databinding.FragmentNavLabRegisterABinding

/**
 *
 *  NavLabRegisterAFragment - 註冊頁面第一頁
 *
 */

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

}