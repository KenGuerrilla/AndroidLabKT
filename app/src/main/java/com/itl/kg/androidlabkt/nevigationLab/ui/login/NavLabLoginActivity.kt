package com.itl.kg.androidlabkt.nevigationLab.ui.login

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavLabLoginViewModelFactory
import com.itl.kg.androidlabkt.nevigationLab.mvvm.NavLabLoginViewModel


/**
 *
 *  NavLabLoginActivity
 *
 *   底下包含登入與註冊程序
 *
 *   註冊完成後回到登入畫面，使用者無法透過返回鍵回到註冊頁面
 *
 *   登入完成後回到Detail畫面
 *
 *   Login的功能做在ActivityViewModel，方便回傳Result
 *
 *   透過ViewModel做UI間的資料溝通與傳遞，這邊就不用FragmentResultListener，感覺非常麻煩...
 *
 */

class NavLabLoginActivity : AppCompatActivity() {

    private val viewModel: NavLabLoginViewModel by viewModels {
        NavLabLoginViewModelFactory()
    }

    companion object {
        const val TAG = "NavLabLoginActivity"
        const val RESULT_LOGIN = "login"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_lab_login)
        val navController = findNavController()
        appBarConfiguration = AppBarConfiguration(navController.graph) // 需要在App Gradle設定KotlinOptions的jvmTarget版本為1.8

//        initResultListener()

        viewModel.loginSuccessLiveData.observe(this, Observer {
            if (it) setResult(Activity.RESULT_OK)
        })
    }

    // 找到位於MainActivity的Fragment容器
    private fun findNavController(): NavController {
        return getFragmentContainer().navController
        // 另一種方式是在xml直接使用fragment，並直接找id就可以了
//        return findNavController(R.id.mainActivity_fragment_container)
    }

    // 官方建議的方式
    private fun getFragmentContainer(): NavHostFragment {
        return supportFragmentManager.findFragmentById(R.id.navLabLoginActivity_fragment_container) as NavHostFragment
    }

}