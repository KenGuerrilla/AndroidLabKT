package com.itl.kg.androidlabkt.nevigationLab.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

/**
 *  帳號設定SP
 */

object NavLabAccountPreferencesHelper {

    private const val PREFERENCES_NAME = "nav_lab_sp"

    private const val ARG_IS_LOGIN = "login"
    private const val ARG_ACCOUNT = "account"

    fun setAccount(account: String, context: Context) {
        getPreferences(context).edit().putString(ARG_ACCOUNT, account).apply()
    }

    fun getAccount(context: Context): String {
        return getPreferences(context).getString(ARG_ACCOUNT, "") ?: ""
    }

    fun setLoginStatus(isLogin: Boolean, context: Context) {
        getPreferences(context).edit().putBoolean(ARG_IS_LOGIN, isLogin).apply()
    }

    fun isLogin(context: Context): Boolean {
        return getPreferences(context).getBoolean(ARG_IS_LOGIN, false)
    }

    fun clearPreferences(context: Context) {
        setAccount("", context)
        setLoginStatus(false, context)
    }


    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    }
}