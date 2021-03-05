package com.itl.kg.androidlabkt.rxJavaLab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.databinding.FragmentRxJavaLabBinding
import com.itl.kg.androidlabkt.rxJavaLab.mvvm.RxJavaViewModel
import com.itl.kg.androidlabkt.rxJavaLab.mvvm.RxJavaViewModelFactory
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable


/**
 *
 *  RxJava簡易Lab
 *
 *  參考資料：https://ithelp.ithome.com.tw/articles/10223619
 *
 */


class RxJavaLabFragment : Fragment() {

    companion object {
        const val TAG = "RxJavaLabFragment"
    }

    private val viewModel: RxJavaViewModel by viewModels {
        RxJavaViewModelFactory()
    }

    private var _binding: FragmentRxJavaLabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRxJavaLabBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.mRxJavaFirstLabBt.setOnClickListener {
            viewModel.firstDemo()
        }

        binding.mRxJavaSecondLabBt.setOnClickListener {
            viewModel.secondDemo()
        }

    }


}