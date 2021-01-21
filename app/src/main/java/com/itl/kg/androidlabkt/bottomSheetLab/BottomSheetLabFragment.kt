package com.itl.kg.androidlabkt.bottomSheetLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itl.kg.androidlabkt.databinding.FragmentBottomSheetLabBinding

/**
 *
 * Bottom Sheet Dialog Fragment fullscreen demo
 *
 * 示範BottomSheetDialogFragment全螢幕實作
 *
 */

class BottomSheetLabFragment : Fragment() {

    private var _binding: FragmentBottomSheetLabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetLabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.mBottomSheetLabShowButton.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheet =
            MyBottomSheetDialogFragment()
        bottomSheet.show(parentFragmentManager, "TAG")
    }

}