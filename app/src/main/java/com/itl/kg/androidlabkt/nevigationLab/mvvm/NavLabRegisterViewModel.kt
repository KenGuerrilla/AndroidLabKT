package com.itl.kg.androidlabkt.nevigationLab.mvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import com.itl.kg.androidlabkt.nevigationLab.repository.NavLabDatabase
import com.itl.kg.androidlabkt.nevigationLab.repository.NavLabAccountPreferencesHelper

/**
 *  RegisterViewModel - 管理註冊流程資料操作
 */

class NavRegisterViewModel : ViewModel() {

    companion object {
        const val TAG = "NavRegisterViewModel"
    }

    private var registerUnit: NavRegisterUnit? = null

    fun setRegisterAccount(account: String, password: String) {
        registerUnit = NavRegisterUnit(account, password)
    }

    // 儲存註冊資訊
    fun saveAccountInfo(context: Context) {
        registerUnit?.run {
            registerAccount(this)
            saveToPreferences(this, context)
        }
    }

    private fun saveToPreferences(unit: NavRegisterUnit, context: Context) {
        NavLabAccountPreferencesHelper.also { sp ->
            sp.setAccount(unit.account, context)
        }
    }

    private fun registerAccount(unit: NavRegisterUnit) {
        NavLabDatabase.register(unit.account, unit.password)
    }

}

data class NavRegisterUnit (
    val account: String,
    val password: String
)