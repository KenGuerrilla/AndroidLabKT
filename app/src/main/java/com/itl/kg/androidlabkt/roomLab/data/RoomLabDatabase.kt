package com.itl.kg.androidlabkt.roomLab.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 *
 * Created by kenguerrilla on 2020/6/12.
 *
 * Database為設定所關聯的Entities與版本資訊等供Compiler產生實作，說明如下
 *  1. Database標籤設定entities與資料庫版本
 *  2. TypeConverters標籤指定資料格式轉換的Converters
 *  3. 定義一個回傳Dao介面的抽象方法讓Compiler產生實作
 *  4. 透過伴生物件 (companion object) 來實作單例模式
 *  5. Room.databaseBuilder().addCallback()可設定當Database建立完成後需要實作的工作
 *
 *
 * 實作產生位置 java(generated) > com.itl.kg.androidlabkt.roomLab.data > RoomLabDatabase_impl
 *
 */


@Database(entities = [RoomLabDataItem::class], version = 1)
@TypeConverters(RoomLabTypeConverters::class)
abstract class RoomLabDatabase : RoomDatabase() {

    abstract fun roomLabDataDao(): RoomLabDataDao

    companion object {

        @Volatile
        private var instance: RoomLabDatabase? = null

        fun getInstance(context: Context): RoomLabDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RoomLabDatabase {
            return Room.databaseBuilder(context, RoomLabDatabase::class.java, "roomLabDatabase")
                .addCallback(object : RoomDatabase.Callback() {
                    // Call back
                })
                .build()
        }
    }


}