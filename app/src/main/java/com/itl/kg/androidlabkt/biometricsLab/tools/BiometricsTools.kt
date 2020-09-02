package com.itl.kg.androidlabkt.biometricsLab.tools

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

/**
 *
 * Created by kenguerrilla on 2020/9/2.
 *
 * Biometrics工具
 *
 *
 * 備註：官方建議Android 10可使用BiometricsManager，而BiometricPrompt仍可正常使用
 *
 * 參考資料：https://developer.android.com/training/sign-in/biometric-auth
 *
 */

class BiometricsTools(
    private val title: String,
    private val subTitle: String,
    private val negBtnText: String) {

    fun startAuth(activity: FragmentActivity, callback: BioToolsCallback) {

        val biometricPrompt = BiometricPrompt(activity, getExecutor(activity), object :
            BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
//                Toast.makeText(activity, "Error: $errorCode", Toast.LENGTH_SHORT).show()
                callback.result(errorCode)
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
//                Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                callback.result(BIO_AUTH_SUCCESS)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
//                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
                callback.result(BIO_AUTH_FAILED)
            }
        })

        biometricPrompt.authenticate(getPromptInfo())

    }

    private fun getPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subTitle)
            .setNegativeButtonText(negBtnText)
            .build()
    }

    private fun getExecutor(context: Context): Executor {
        return ContextCompat.getMainExecutor(context)
    }


    companion object {

        const val BIO_AUTH_SUCCESS = 0
        const val BIO_AUTH_FAILED = 901

        fun checkDeviceSupport(context: Context): Int {
            val biometricManager = BiometricManager.from(context)
            return biometricManager.canAuthenticate()
        }
    }
}

interface BioToolsCallback {
    fun result(result: Int)
}