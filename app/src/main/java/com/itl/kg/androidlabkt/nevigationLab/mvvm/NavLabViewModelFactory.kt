package com.itl.kg.androidlabkt.nevigationLab.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class NavRegisterViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NavRegisterViewModel() as T
    }
}

class NavLabLoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NavLabLoginViewModel() as T
    }
}

class NavLabMainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NavLabMainViewModel() as T
    }

}