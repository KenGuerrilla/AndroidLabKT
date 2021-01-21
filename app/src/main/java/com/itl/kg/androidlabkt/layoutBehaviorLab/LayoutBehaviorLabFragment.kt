package com.itl.kg.androidlabkt.layoutBehaviorLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.itl.kg.androidlabkt.databinding.FragmentLayoutBehaviorLabBinding
import com.itl.kg.androidlabkt.getWindowHeight

/**
 *
 * Layout Behavior Lab Demo
 *
 * 示範Bottom Sheet與相關元件透過CoordinatorLayout與Behavior做連動
 *
 */

class LayoutBehaviorLabFragment : Fragment() {

    private var _binding: FragmentLayoutBehaviorLabBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomBehavior: BottomSheetBehavior<View>
    private lateinit var bottomSheet: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLayoutBehaviorLabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheet = binding.layoutBehaviorLabFragmentBottomSheetFrameLayout
        bottomBehavior = BottomSheetBehavior.from(bottomSheet)

        val params = bottomSheet.layoutParams
        val height =
            getWindowHeight(requireActivity()) - binding.layoutBehaviorLabFragmentMainTextTextView.layoutParams.height

        params.height = height
        bottomSheet.layoutParams = params

        // 設定Click even，點下後將BottomSheet展開
        binding.layoutBehaviorLabFragmentMainTextTextView.setOnClickListener {
            bottomBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

}