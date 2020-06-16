package com.itl.kg.androidlabkt.roomLab.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itl.kg.androidlabkt.roomLab.data.RoomLabRepository

/**
 *
 * Created by kenguerrilla on 2020/6/15.
 *
 */

class RoomLabViewModelFactory(
    private val repository: RoomLabRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomLabDataViewModel(repository) as T
    }
}