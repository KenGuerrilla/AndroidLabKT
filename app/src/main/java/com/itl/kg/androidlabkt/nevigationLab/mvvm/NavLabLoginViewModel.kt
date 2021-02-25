package com.itl.kg.androidlabkt.nevigationLab.mvvm

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itl.kg.androidlabkt.nevigationLab.repository.LoginResult
import com.itl.kg.androidlabkt.nevigationLab.repository.NavLabDatabase
import com.itl.kg.androidlabkt.nevigationLab.repository.NavLabAccountPreferencesHelper


class NavLabLoginViewModel : ViewModel() {
    
    companion object {
        const val TAG = "NavLoginViewModel"
    }

    val accountLiveData: MutableLiveData<String> = MutableLiveData()
    val loginResultLiveData: MutableLiveData<LoginResult> = MutableLiveData()
    val loginSuccessLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getLastLoginAccount(context: Context) {
        accountLiveData.value = NavLabAccountPreferencesHelper.getAccount(context)
    }

    fun login(account: String, password: String) {
        Log.d(TAG, "login: $account / $password")
        val result = NavLabDatabase.login(account, password)
        loginResultLiveData.value = result
    }

    fun saveLoginStatus(isLogin: Boolean, context: Context) {
        NavLabAccountPreferencesHelper.setLoginStatus(isLogin, context)
    }

    fun loginSuccess() {
        loginSuccessLiveData.value = true
    }

}