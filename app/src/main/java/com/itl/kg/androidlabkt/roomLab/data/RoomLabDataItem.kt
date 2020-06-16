package com.itl.kg.androidlabkt.roomLab.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 *
 * Created by kenguerrilla on 2020/6/12.
 *
 * Entry為設定資料表內容與格式，說明如下
 *  1. Entity標籤定義資料表的名稱
 *  2. data class當中ColumnInfo則定義各資料成員於table中所指定的名稱
 *  3. PrimaryKey則是指定某個成員為鍵值，設定autoGenerate為true表示為自動產生
 *
 */

@Entity(tableName = "roomLab_data")
data class RoomLabDataItem(
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "createTime") val createTime: Calendar = Calendar.getInstance(),
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var itemId: Long = 0
) {
    override fun toString(): String {
        return "$text / ${createTime.time} / $itemId"
    }
}