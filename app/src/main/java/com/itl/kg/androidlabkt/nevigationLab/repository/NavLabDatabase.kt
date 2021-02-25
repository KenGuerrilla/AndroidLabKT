package com.itl.kg.androidlabkt.nevigationLab.repository

import com.itl.kg.androidlabkt.nevigationLab.repository.LoginResult.Companion.RESULT_ERROR
import com.itl.kg.androidlabkt.nevigationLab.repository.LoginResult.Companion.RESULT_OK

/**
 *  資料庫Mock物件
 */

object NavLabDatabase {

    const val TAG = "NavLabDatabase"

    private val loginDataList = mutableListOf<AccountInfo>().apply {
        // default account
        this.add(AccountInfo("aaa", "1111"))
        this.add(AccountInfo("ken", "111"))
    }

    fun login(account: String, password: String): LoginResult {
        for (item in loginDataList) {
            if (item.isCorrect(account, password))
                return LoginResult(RESULT_OK, "Success!!")
        }
        return LoginResult(RESULT_ERROR, "Login Failed!")
    }

    fun register(account: String, password: String) {
        val item = AccountInfo(account, password)
        loginDataList.add(item)
    }

}

class LoginResult(
    val result: Int,
    val message: String
) {
    companion object {
        const val RESULT_OK = 200
        const val RESULT_ERROR = 400
    }
}

data class AccountInfo(private val account: String, private val password: String) {
    fun isCorrect(authAccount: String, authPassword: String): Boolean {
        return authAccount == account && authPassword == password
    }
}