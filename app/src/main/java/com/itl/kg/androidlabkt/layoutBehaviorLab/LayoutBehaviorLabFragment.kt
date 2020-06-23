package com.itl.kg.androidlabkt.layoutBehaviorLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.getWindowHeight
import kotlinx.android.synthetic.main.fragment_layout_behavior_lab.*

/**
 *
 * Layout Behavior Lab Demo
 *
 * 示範Bottom Sheet與相關元件透過CoordinatorLayout與Behavior做連動
 *
 */

class LayoutBehaviorLabFragment : Fragment() {

    private lateinit var bottomBehavior: BottomSheetBehavior<View>
    private lateinit var bottomSheet: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout_behavior_lab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheet = layoutBehaviorLabFragment_bottomSheet_frameLayout
        bottomBehavior = BottomSheetBehavior.from(bottomSheet)

        val params = bottomSheet.layoutParams
        val height =
            getWindowHeight(requireActivity()) - layoutBehaviorLabFragment_mainText_textView.layoutParams.height

        params.height = height
        bottomSheet.layoutParams = params

        // 設定Click even，點下後將BottomSheet展開
        layoutBehaviorLabFragment_mainText_textView.setOnClickListener {
            bottomBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

}