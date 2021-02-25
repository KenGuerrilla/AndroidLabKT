package com.itl.kg.androidlabkt.nevigationLab.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.itl.kg.androidlabkt.R

/**
 *  NavLabRegisterActivity - 註冊流程
 *
 *  這邊的流程為展示Navigation action的操作
 *
 *  註冊過程中途離開都應該回到NavLabRegisterAFragment
 *
 *  註冊完成後則要關閉Activity回到Main，並檢查是否登入成功，成功就跳到DetailFragment
 *
 *
 *
 *  ps.如果註冊流程較為複雜，則可以與各Fragment共用一個ViewModel依序完成註冊資料後送出
 *
 *  >> Toolbar的部分因為返回鍵與系統返回鍵行為無法一致，因此先拿掉
 *
 */

class NavLabRegisterActivity : AppCompatActivity() {

    companion object {
        const val TAG = "NavLabRegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_lab_register)
//        initToolbarNavigate()
    }

    // 官方建議的方式
    private fun getFragmentContainer(): NavHostFragment {
        return supportFragmentManager.findFragmentById(R.id.navLabRegisterActivity_fragment_container) as NavHostFragment
    }


    // TODO 因為系統返回按鈕與Toolbar按鈕行為無法一致，待後續想辦法處理

//    private lateinit var appBarConfiguration: AppBarConfiguration

//    private fun initToolbarNavigate() {
//        val navController = findNavController()
//        val toolbar = findViewById<Toolbar>(R.id.navigation_lab_register_toolbar)
//        appBarConfiguration = AppBarConfiguration(navController.graph) // 需要在App Gradle設定KotlinOptions的jvmTarget版本為1.8
//
//        setupWithNavController(toolbar, navController)
//    }

//    private fun setupWithNavController(toolbar: Toolbar, navController: NavController) {
//        toolbar.setupWithNavController(navController, appBarConfiguration)
//    }

    // 找到位於MainActivity的Fragment容器
    private fun findNavController(): NavController {
        return getFragmentContainer().navController
        // 另一種方式是在xml直接使用fragment，並直接找id就可以了
//        return findNavController(R.id.mainActivity_fragment_container)
    }

}