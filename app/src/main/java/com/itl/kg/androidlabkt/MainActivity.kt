package com.itl.kg.androidlabkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

/**
 *
 * 1. 將Toolbar透過Navigate初始化，讓Toolbar可以與Navigate Component做連動
 * 2. Toolbar的Label可以在nav_main_graph做更改
 *
 */

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbarNavigate()

    }

    private fun initToolbarNavigate() {
        val navController = findNavController()

        appBarConfiguration = AppBarConfiguration(navController.graph) // 需要在App Gradle設定KotlinOptions的jvmTarget版本為1.8
        findViewById<Toolbar>(R.id.mainActivity_toolbar).setupWithNavController(navController, appBarConfiguration)
    }

    // 找到位於MainActivity的Fragment容器
    private fun findNavController(): NavController {
        // 官方建議的方式
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainActivity_fragment_container) as NavHostFragment
        return navHostFragment.navController
        // 另一種方式是在xml直接使用fragment，並直接找id就可以了
//        return findNavController(R.id.mainActivity_fragment_container)
    }

}