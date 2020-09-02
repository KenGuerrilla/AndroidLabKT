package com.itl.kg.androidlabkt.biometricsLab.tools

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt

/**
 *
 * BiometricPrompt error code parser
 *
 *  建議參照BiometricPrompt或BiometricManager內部之狀態碼依照需求翻譯
 *
 *  參考資料：https://developer.android.com/reference/androidx/biometric/BiometricPrompt#AUTHENTICATION_RESULT_TYPE_BIOMETRIC
 *
 */
class SimpleBioErrorCodeParser : BiometricsErrorCodeParserInterface {

    override fun parserAuthErrorCode(context: Context, code: Int): String {
        return when(code) {
            BiometricPrompt.ERROR_CANCELED -> "Biometric動作取消"
            BiometricPrompt.ERROR_HW_NOT_PRESENT -> "裝置硬體不支援Biometric"
            BiometricPrompt.ERROR_HW_UNAVAILABLE -> "Biometric目前無法使用，請稍後再試"
            BiometricPrompt.ERROR_LOCKOUT -> "驗證失敗超過五次，請三十秒後再試"
            BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> "嘗試過多，Biometric鎖定，強制使用替代驗證"
            BiometricPrompt.ERROR_NEGATIVE_BUTTON -> "按下Negative Button"
            BiometricPrompt.ERROR_NO_BIOMETRICS -> "裝置尚未啟用Biometrics"
            BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL -> "裝置尚未啟用任何驗證方式，如PIN碼、圖案或密碼等"
            BiometricPrompt.ERROR_NO_SPACE -> "No space"
            BiometricPrompt.ERROR_TIMEOUT -> "操作逾時"
            BiometricPrompt.ERROR_UNABLE_TO_PROCESS -> "感應器處理錯誤"
            BiometricPrompt.ERROR_USER_CANCELED -> "使用者取消操作"
            BiometricPrompt.ERROR_VENDOR -> "Vendor"

            BiometricsTools.BIO_AUTH_FAILED -> "驗證失敗"

            else -> "驗證成功"
        }

    }

    override fun parserStatusErrorCode(context: Context, code: Int): String {
        return when(code) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> "Biometric目前無法使用，請稍後再試"
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> "裝置尚未啟用Biometric"
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> "裝置硬體不支援Biometric"
            else -> "Biometric可正常使用"
        }
    }
}

interface BiometricsErrorCodeParserInterface {
    fun parserAuthErrorCode(context: Context, code: Int): String
    fun parserStatusErrorCode(context: Context, code: Int): String
}