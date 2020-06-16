package com.itl.kg.androidlabkt.roomLab.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itl.kg.androidlabkt.roomLab.data.RoomLabDataItem
import com.itl.kg.androidlabkt.roomLab.data.RoomLabRepository
import kotlinx.coroutines.launch

/**
 *
 * Created by kenguerrilla on 2020/6/15.
 *
 */

class RoomLabDataViewModel internal constructor(
    private  val repository: RoomLabRepository
): ViewModel() {

    fun getDataItemList() = repository.getDataList()

    fun addDataItem(item: RoomLabDataItem) {
        viewModelScope.launch {
            repository.addDataToList(item)
        }
    }

    fun deleteItem(item: RoomLabDataItem) {
        viewModelScope.launch {
            repository.deleteData(item)
        }
    }

}