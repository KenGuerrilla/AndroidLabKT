package com.itl.kg.androidlabkt.bottomSheetLab

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itl.kg.androidlabkt.databinding.BottomSheetDemoLayoutBinding

/**
 *
 * Created by kenguerrilla on 2020/6/22.
 *
 */
class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDemoLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetDemoLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheetDialog = dialog as BottomSheetDialog
            setupDialog(bottomSheetDialog)
        }

        return dialog
    }

    private fun setupDialog(bottomSheetDialog: BottomSheetDialog) {
        // 取出底層的FrameLayout，如果要實作Dialog圓導角樣式就必須將Style設定於此
        val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet as View)
        val layoutParams = bottomSheet.layoutParams

        // 記得將設定過後的LayoutParams丟回FrameLayout
        val height = getWindowHeight()
        layoutParams.height = height
        bottomSheet.layoutParams = layoutParams

        // 跳過Collapsed狀態
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        // Collapsed狀態時的高度
//        behavior.peekHeight = 100
    }

    // 取得螢幕高度
    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}
