package com.itl.kg.androidlabkt.customViewLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itl.kg.androidlabkt.R
import kotlinx.android.synthetic.main.fragment_custom_view_lab.*

/**
 *
 * 步驟一 >> 實作TitleContentTextView類別
 * 1. 物件需要繼承CustomViewLayout最外層的類別，例如這邊就是ConstraintLayout
 * 2. 定義當中所使用到的View物件，這邊是兩個TextView
 * 3. 透過init方法初始化相關物件
 * 4. 設置View物件的get與set方法，這邊是透過方法更改TextView當中的內容
 *
 * 步驟二 >> 實作attrs定義
 * 1. 在res當中的values資料夾新增一個attrs.xml檔案
 * 2. 新增一組<declare-styleable>標籤，其name為剛剛實作類別的名稱，這邊則為TitleContentTextView
 * 3. 在<declare-styleable>標籤內層新增相對應的屬性，這邊則為title與content的數值，格式為string
 * 4. 完成後重新編譯專案 << 很重要，否則Layout預覽畫面可能會有問題 >>
 *
 * 步驟三 >> 使用CustomView
 * 1. 在欲使用的Layout中輸入package路徑標籤即可找到剛剛定義的CustomView
 * 2. 使用app開頭找到方才定義的set與get方法或其他自訂義的方法
 *
 */

class CustomViewLab : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_view_lab, container, false)
    }

    override fun onResume() {
        super.onResume()
        customViewLab_textViewA.setTitle("Resume title")
        customViewLab_textViewA.setContent("Resume Content")
    }

}