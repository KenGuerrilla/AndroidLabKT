package com.itl.kg.androidlabkt.biometricsLab.tools

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.security.KeyStore
import java.util.concurrent.Executor
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 *
 * Created by kenguerrilla on 2020/9/2.
 *
 * Biometrics工具
 *
 *
 * 備註：官方文件表示Android 10可使用BiometricsManager，而BiometricPrompt亦可。
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

        val cryptoTool = BioCryptoTool() // 使用Crypto工具取得Cipher

        biometricPrompt.authenticate(getPromptInfo(),
            BiometricPrompt.CryptoObject(cryptoTool.getCryptoCipher()))

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


/**
 *
 * Biometric驗證之CryptoObject產生工具
 *
 * 程式碼依照官方教學修改而成，可參閱參考資料
 *
 *
 * 參考資料：https://developer.android.com/training/sign-in/biometric-auth#kotlin
 *
 */

class BioCryptoTool {

    private val bioKeyName = "BioAndroidLabKT"
    private val androidKeyStoreName = "AndroidKeyStore"
    private val keyStore = KeyStore.getInstance(androidKeyStoreName)

    init {
        // KeyStore一定要load，否則會掛掉噴InvocationTargetException
        keyStore.load(null)
    }

    // 取得設定完成之Cipher
    fun getCryptoCipher(): Cipher {

        if (isNotContainsKey()) {
            generateSecretKey(getKeyGenParameterSpec())
        }

        val cipher = getCipher()
        val secretKey = getSecretKey()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher
    }

    // 檢查KeyStore是否有對應的Key，避免重複產生Key的問題
    private fun isNotContainsKey(): Boolean {
        return !keyStore.containsAlias(bioKeyName)
    }

    // 產生Key
    private fun generateSecretKey(keyGenParameterSpec: KeyGenParameterSpec) {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES, androidKeyStoreName)
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    // 從KeyStore取得SecretKey
    private fun getSecretKey(): SecretKey {
        return keyStore.getKey(bioKeyName, null) as SecretKey
    }


    // 產生Cipher
    private fun getCipher(): Cipher {
        return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7)
    }

    private fun getKeyGenParameterSpec(): KeyGenParameterSpec {
        return KeyGenParameterSpec.Builder(
            bioKeyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .setUserAuthenticationRequired(true)
            // Invalidate the keys if the user has registered a new biometric
            // credential, such as a new fingerprint. Can call this method only
            // on Android 7.0 (API level 24) or higher. The variable
            // "invalidatedByBiometricEnrollment" is true by default.
            .setInvalidatedByBiometricEnrollment(true)
            .build()
    }

}