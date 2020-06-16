package com.itl.kg.androidlabkt.roomLab.utilities

import android.content.Context
import com.itl.kg.androidlabkt.roomLab.data.RoomLabDatabase
import com.itl.kg.androidlabkt.roomLab.data.RoomLabRepository
import com.itl.kg.androidlabkt.roomLab.viewModel.RoomLabViewModelFactory

/**
 *
 * Created by kenguerrilla on 2020/6/15.
 *
 * 實作注入性依賴的物件
 *
 */
object InjectorUtils {

    private fun getRoomLabRepository(context: Context): RoomLabRepository {
        return RoomLabRepository.getInstance(
            RoomLabDatabase.getInstance(context.applicationContext).roomLabDataDao()
        )
    }

    fun provideRoomLabDataViewModelFactory(context: Context): RoomLabViewModelFactory {
        val repository = getRoomLabRepository(context)
        return RoomLabViewModelFactory(repository)
    }

}