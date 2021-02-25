package com.itl.kg.androidlabkt.nevigationLab.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.itl.kg.androidlabkt.R


/**
 *
 *  Navigation測試Demo - 這個Demo使用Navigation來做個畫面的管理，實作幾個可能常用到的功能
 *
 *  NavigationLabActivity畫面包含一個Fragment container，用來顯示架構底下的Fragment畫面
 *
 *  其中container上方實作一個Toolbar，其交由Navigation託管顯示標題與返回鍵
 *
 *
 *  備註：將Toolbar交由Navigation託管後建議不要再對其實做相關功能，避免行為上的衝突
 *
 *  備註：關於NavHostFragment的問題，由於IDE編譯連結的情況，因此新增NavHostFragment之後在NavGraph
 *  仍會顯示not found的情況，原則上運作都沒問題，很在意的話只需要重開IDE或專案即可。
 *  可參考：https://developer.android.com/guide/navigation/navigation-getting-started#add-navhost
 */

class NavigationLabActivity : AppCompatActivity() {

    companion object {
        const val TAG = "NavigationLabActivity"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_lab)

        initToolbarNavigate()
    }

    private fun initToolbarNavigate() {
        val navController = findNavController()
        val toolbar = findViewById<Toolbar>(R.id.navigation_lab_toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph) // 需要在App Gradle設定KotlinOptions的jvmTarget版本為1.8

        setupWithNavController(toolbar, navController)
    }


    // 將toolbar給Navigation處理，包含Title與返回按鍵
    // 這邊要注意一件事情，當back stick count為0的時候，Toolbar的返回按鍵會消失
    // 例如現在的Demo要回到MainActivity選單，需要依靠系統本身的NavigationUp按鈕返回選單
    // 可以想成NavigationActivity是個別不同的群組(不同的NavController)
    // 或者是在MainActivity的Navigation底下使用NestGraph來做Fragment的分類

    // 這邊要實作NestGraph，因此特別分出一個Activity來做區別。
    private fun setupWithNavController(toolbar: Toolbar, navController: NavController) {
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    // 使用原先SupportActionBar的管理方式
    // 需要另外處理Title與onSupportNavigationUp按鈕的事件
    private fun setupWithDefaultSupportActionBar(toolbar: Toolbar, navController: NavController) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getFragmentContainer().childFragmentManager.addOnBackStackChangedListener {
            Log.d(TAG, "Back stack count: ${getFragmentContainer().childFragmentManager.backStackEntryCount}")
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }

    // 找到位於MainActivity的Fragment容器
    private fun findNavController(): NavController {
        return getFragmentContainer().navController
        // 另一種方式是在xml直接使用fragment，並直接找id就可以了
//        return findNavController(R.id.mainActivity_fragment_container)
    }

    // 官方建議的方式
    private fun getFragmentContainer(): NavHostFragment {
        return supportFragmentManager.findFragmentById(R.id.navLabMainActivity_fragment_container) as NavHostFragment
    }

}