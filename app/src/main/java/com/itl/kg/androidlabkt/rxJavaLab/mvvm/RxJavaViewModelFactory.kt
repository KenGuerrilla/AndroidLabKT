package com.itl.kg.androidlabkt.rxJavaLab.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class RxJavaViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RxJavaViewModel() as T
    }
}