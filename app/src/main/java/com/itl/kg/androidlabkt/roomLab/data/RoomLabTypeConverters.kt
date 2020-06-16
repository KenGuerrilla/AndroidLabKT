package com.itl.kg.androidlabkt.roomLab.data

import androidx.room.TypeConverter
import java.util.*

/**
 *
 * Created by kenguerrilla on 2020/6/12.
 *
 * Converters為Room的資料轉換器
 *
 * 此處是DataItem存入Database之前先將其Calendar物件轉換為timeInMillis方便儲存，取出時則反向轉換，不用額外處理資料轉換的問題
 *
 */

class RoomLabTypeConverters {
    @TypeConverter
    fun calendarToDatesTamp(calendar: Calendar): Long = calendar.timeInMillis
    @TypeConverter
    fun datesTampToCalendar(value: Long): Calendar = Calendar.getInstance().apply { timeInMillis = value }
}