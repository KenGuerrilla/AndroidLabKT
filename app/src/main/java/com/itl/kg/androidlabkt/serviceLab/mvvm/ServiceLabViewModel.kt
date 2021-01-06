package com.itl.kg.androidlabkt.serviceLab.mvvm

import androidx.lifecycle.ViewModel
import com.itl.kg.androidlabkt.checkDeviceSdkVersion

class ServiceLabViewModel : ViewModel() {

    fun checkDeviceSdk(target: Int): Boolean {
        return checkDeviceSdkVersion(target)
    }

}