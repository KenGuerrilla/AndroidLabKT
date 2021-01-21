package com.itl.kg.androidlabkt.keyFrameAnimLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.databinding.FragmentKeyFrameAnimationsLabStartBinding

/**
 *  ConstraintLayout簡易View元件動畫
 *
 *  參考資料：https://developer.android.com/training/constraint-layout
 */

class KeyFrameAnimationsLabFragment : Fragment() {

    private var _binding: FragmentKeyFrameAnimationsLabStartBinding? = null
    private val binding get() = _binding!!

    lateinit var button: Button
    private var isBottom = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKeyFrameAnimationsLabStartBinding.inflate(inflater, container, false)
        initListener()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListener() {
        binding.button.setOnClickListener {
            if (isBottom) {
                animateToKeyframeOne()
            } else {
                animateToKeyframeTwo()
            }
        }
    }

    private fun animateToKeyframeOne() {
        val constraintSet = ConstraintSet()
        constraintSet.load(requireActivity(), R.layout.fragment_key_frame_animations_lab_start)
        TransitionManager.beginDelayedTransition(binding.animationsConstraintLayout)
        constraintSet.applyTo(binding.animationsConstraintLayout)
        isBottom = false
    }

    private fun animateToKeyframeTwo() {
        val constraintSet = ConstraintSet()
        constraintSet.load(requireActivity(), R.layout.fragment_key_frame_animations_lab_end)
        TransitionManager.beginDelayedTransition(binding.animationsConstraintLayout)
        constraintSet.applyTo(binding.animationsConstraintLayout)
        isBottom = true
    }

}