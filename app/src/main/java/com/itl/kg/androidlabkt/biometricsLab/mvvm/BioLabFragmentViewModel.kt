package com.itl.kg.androidlabkt.biometricsLab.mvvm

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itl.kg.androidlabkt.biometricsLab.tools.BioToolsCallback
import com.itl.kg.androidlabkt.biometricsLab.tools.BiometricsTools
import com.itl.kg.androidlabkt.biometricsLab.tools.SimpleBioErrorCodeParser

/**
 *
 * BioLabFragmentViewModel
 *
 */
class BioLabFragmentViewModel : ViewModel() {

    private val authResultLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getAuthResultLiveData(): LiveData<Int> = authResultLiveData

    fun checkDeviceSupport(context: Context): Int {
        return BiometricsTools.checkDeviceSupport(context)
    }

    fun startBioAuth(
        activity: FragmentActivity,
        title: String,
        subTitle: String,
        negBtnText: String) {

        val tool = getBiometricsTools(title, subTitle, negBtnText)
        tool.startAuth(activity, getBioToolsCallback())

    }

    private fun getBiometricsTools(
        title: String,
        subTitle: String,
        negBtnText: String
    ): BiometricsTools {
        return BiometricsTools(title, subTitle, negBtnText)
    }

    fun parserAuthErrorCode(context: Context, errorCode: Int): String {
        return SimpleBioErrorCodeParser().parserAuthErrorCode(context, errorCode)
    }

    fun parserStatusErrorCode(context: Context, errorCode: Int): String {
        return SimpleBioErrorCodeParser().parserStatusErrorCode(context, errorCode)
    }

    private fun getBioToolsCallback(): BioToolsCallback {
        return object : BioToolsCallback {
            override fun result(result: Int) {
                authResultLiveData.value = result
            }
        }
    }

}