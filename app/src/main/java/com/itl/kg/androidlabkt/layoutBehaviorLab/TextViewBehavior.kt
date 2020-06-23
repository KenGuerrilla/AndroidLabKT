package com.itl.kg.androidlabkt.layoutBehaviorLab

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.getWindowHeight
import kotlin.math.abs

/**
 *
 * Created by kenguerrilla on 2020/6/22.
 *
 * child - Main content
 * dependency - Bottom sheet
 *
 */
class TextViewBehavior(val context: Context, attr: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        // 判斷Dependency是不是BottomSheet，這邊的BottomSheet底層Layout為FrameLayout
        return dependency is FrameLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val windowHeight = getWindowHeight(context as Activity)

        // Main content height = windowHeight - ( windowHeight - abs(BottomSheetPosition.y)
        val childHeight = windowHeight - (windowHeight - abs(dependency.y))

        // Update textView about content height
        val contentTextView =
            child.findViewById<TextView>(R.id.layoutBehaviorLabFragment_mainText_textView)
        contentTextView.text = childHeight.toString()

        val childParams = child.layoutParams
        childParams.height = childHeight.toInt()
        child.layoutParams = childParams

        return true
    }
}