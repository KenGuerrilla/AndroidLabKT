package com.itl.kg.androidlabkt

import android.app.Activity
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun getWindowHeight(activity: Activity): Int {
    val displayMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

class TimeCalculateTool {
    fun getTimeString(date: Date): String {
        val format = SimpleDateFormat("HH:mm:ss", Locale.TAIWAN)
        return format.format(date)
    }

    fun getTimeDifference(start: Date, end: Date): String {
        return parserMillisecondsToString(end.time - start.time)
    }

    private fun parserMillisecondsToString(diffLong: Long): String {
        val totalSeconds = TimeUnit.SECONDS.convert(diffLong, TimeUnit.MILLISECONDS).toInt()
        val totalMinute = TimeUnit.MINUTES.convert(diffLong, TimeUnit.MILLISECONDS).toInt()
        val totalHour = TimeUnit.HOURS.convert(diffLong, TimeUnit.MILLISECONDS).toInt()

        return String.format("%d:%02d:%02d", totalHour, totalMinute % 60, totalSeconds % 60)
    }

}