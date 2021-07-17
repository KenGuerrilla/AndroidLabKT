package com.itl.kg.androidlabkt.roomLab.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
 * Created by kenguerrilla on 2020/6/12.
 *
 * Dao為操作Database介面，定義介面後由Compiler產生實作
 *  a. 資料庫操作標籤跟語法與SQLite的操作相同
 *  b. Insert標籤可另外指定衝突時所採取的動作，此處指定為Replace，詳細可參考官方文件
 *  c. 可在這裡指定回傳資料為LiveData
 *
 * 實作產生位置 java(generated) > com.itl.kg.androidlabkt.roomLab.data > RoomLabDataDao_Impl
 *
 */
@Dao
interface RoomLabDataDao {

    @Query("SELECT * FROM roomLab_data ORDER BY id")
    fun getDataList(): LiveData<List<RoomLabDataItem>>

    @Update
    suspend fun update(vararg roomLabDataItem: RoomLabDataItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(dataItem: RoomLabDataItem)

    @Delete
    suspend fun deleteDataItem(dataItem: RoomLabDataItem)

}