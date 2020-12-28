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

/**
 *  ConstraintLayout簡易View元件動畫
 *
 *  參考資料：https://developer.android.com/training/constraint-layout
 */

class KeyFrameAnimationsLabFragment : Fragment() {

    lateinit var constraintLayout: ConstraintLayout
    lateinit var button: Button
    private var isBottom = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_key_frame_animations_lab_start, container, false)
        constraintLayout = view.findViewById(R.id.animationsConstraintLayout)
        button = view.findViewById(R.id.button)
        button.setOnClickListener {
            if (isBottom) {
                animateToKeyframeOne()
            } else {
                animateToKeyframeTwo()
            }
        }
        return view
    }

    private fun animateToKeyframeOne() {
        val constraintSet = ConstraintSet()
        constraintSet.load(requireActivity(), R.layout.fragment_key_frame_animations_lab_start)
        TransitionManager.beginDelayedTransition(constraintLayout)
        constraintSet.applyTo(constraintLayout)
        isBottom = false
    }

    private fun animateToKeyframeTwo() {
        val constraintSet = ConstraintSet()
        constraintSet.load(requireActivity(), R.layout.fragment_key_frame_animations_lab_end)
        TransitionManager.beginDelayedTransition(constraintLayout)
        constraintSet.applyTo(constraintLayout)
        isBottom = true
    }

}