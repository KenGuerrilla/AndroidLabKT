package com.itl.kg.androidlabkt.nevigationLab.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.itl.kg.androidlabkt.databinding.FragmentNavLabRegisterBBinding

class NavLabRegisterBFragment : Fragment() {

    private var _binding: FragmentNavLabRegisterBBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNavLabRegisterBBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.mLabRegisterSecondBtn.setOnClickListener {
            it.findNavController().navigate(NavLabRegisterBFragmentDirections.actionNavLabRegisterBFragmentToNavLabRegisterCFragment())
        }
    }

}