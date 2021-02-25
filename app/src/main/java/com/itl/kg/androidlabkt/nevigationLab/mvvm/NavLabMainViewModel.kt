package com.itl.kg.androidlabkt.nevigationLab.mvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import com.itl.kg.androidlabkt.nevigationLab.repository.NavLabAccountPreferencesHelper


/**
 *  MainViewModel - 檢查登入狀態與登出
 */

class NavLabMainViewModel : ViewModel() {
    fun isLogin(context: Context): Boolean {
        return NavLabAccountPreferencesHelper.isLogin(context)
    }

    fun logout(context: Context): Boolean {
        NavLabAccountPreferencesHelper.apply {
            clearPreferences(context)
        }
        return true
    }
}