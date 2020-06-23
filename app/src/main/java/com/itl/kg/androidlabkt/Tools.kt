package com.itl.kg.androidlabkt

import android.app.Activity
import android.util.DisplayMetrics

/**
 *
 * Created by kenguerrilla on 2020/6/23.
 *
 */

fun getWindowHeight(activity: Activity): Int {
    val displayMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}