package com.itl.kg.androidlabkt.bottomSheetLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itl.kg.androidlabkt.R
import kotlinx.android.synthetic.main.fragment_bottom_sheet_lab.*

/**
 *
 * Bottom Sheet Dialog Fragment fullscreen demo
 *
 * 示範BottomSheetDialogFragment全螢幕實作
 *
 */

class BottomSheetLabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet_lab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        bottomSheetLab_show_button.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheet =
            MyBottomSheetDialogFragment()
        bottomSheet.show(parentFragmentManager, "TAG")
    }

}