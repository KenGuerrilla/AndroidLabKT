package com.itl.kg.androidlabkt.roomLab.data

/**
 *
 * Created by kenguerrilla on 2020/6/15.
 *
 * RoomLabRepository為資料來源的實作
 *
 */

class RoomLabRepository(private val roomLabDataDao: RoomLabDataDao) {

    fun getDataList() = roomLabDataDao.getDataList()

    suspend fun addDataToList(item: RoomLabDataItem) {
        roomLabDataDao.insertData(item)
    }

    suspend fun deleteData(item: RoomLabDataItem) {
        roomLabDataDao.deleteDataItem(item)
    }

    companion object {

        @Volatile
        private var instance: RoomLabRepository? = null

        fun getInstance(dataDao: RoomLabDataDao) = instance ?: synchronized(this) {
            instance ?: RoomLabRepository(dataDao).also { instance = it }
        }
    }
}