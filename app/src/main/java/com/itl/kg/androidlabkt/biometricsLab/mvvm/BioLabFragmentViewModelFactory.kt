package com.itl.kg.androidlabkt.biometricsLab.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *
 * Created by kenguerrilla on 2020/9/2.
 *
 */
class BioLabFragmentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BioLabFragmentViewModel() as T
    }
}