package com.itl.kg.androidlabkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

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
        return findNavController(R.id.mainActivity_fragment_container)
    }

}