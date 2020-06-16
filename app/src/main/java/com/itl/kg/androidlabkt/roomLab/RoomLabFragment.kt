package com.itl.kg.androidlabkt.roomLab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.itl.kg.androidlabkt.R
import com.itl.kg.androidlabkt.roomLab.data.RoomLabDataItem
import com.itl.kg.androidlabkt.roomLab.utilities.InjectorUtils
import com.itl.kg.androidlabkt.roomLab.viewModel.RoomLabDataViewModel
import kotlinx.android.synthetic.main.fragment_room_lab.*

/**
 *
 * Room Lab - Androidx jetpack Room components with MVVM demo
 *
 * 這個Demo是由官方範例sunflower專案簡化而成，連結於參考資料
 *
 * 使用技術如下
 *  a. Room
 *  b. MVVM
 *
 *
 * 相關說明如下
 *
 *  1. MVVM用於畫面資料的更新，可查看viewModel package相關物件
 *  2. Room則實作於Repository，可查看data package相關物件
 *  3. ViewModel的部分則是使用委託的方式交給androidx.fragment.app.Fragment進行實作，並由InjectorUtils取得注入性依賴的ViewModelFactory實作
 *  4. 由於使用kotlin-android-extensions的關係，取得View物件應在畫面建立之後( onViewCreated )，否則會產生NPE的問題
 *  5. Demo是使用Kotlin，所以要在Gradle app dependencies 加上 kapt "androidx.room:room-compiler:_版本號_"，否則無法產生相對應的物件實作
 *
 *
 * Room 主要由下列三個Components組成，詳細說明請參照Lab中data package各物件說明
 *  a. Database
 *  b. Entity
 *  c. Data Access Objects
 *
 *
 * 參考資料
 * MVVM - https://developer.android.com/jetpack/docs/guide
 * Room - https://developer.android.com/training/data-storage/room
 * Sunflower project - https://github.com/android/sunflower/
 *
 */

class RoomLabFragment : Fragment() {

    private var adapter: RoomLabListAdapter? = null

    private val viewModel: RoomLabDataViewModel by viewModels {
        InjectorUtils.provideRoomLabDataViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_room_lab, container, false)
    }

    // View物件參數的初始化應實作晚於或此處，早於onViewCreated則會有NPE的問題
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeUi()
    }

    private fun initView() {
        roomLab_add_button.setOnClickListener {
            val item = RoomLabDataItem(roomLab_editText.text.toString())
            viewModel.addDataItem(item)
            roomLab_editText.editableText.clear()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = RoomLabListAdapter(listOf())

        adapter?.itemClickListener = object : RoomLabDataListAdapterOnItemClickListener {
            override fun onItemClick(item: RoomLabDataItem, position: Int) {
                viewModel.deleteItem(item)
            }
        }

        // Init RecyclerView
        roomLab_recyclerView.also {
            it.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
        }
    }

    private fun subscribeUi() {
        // Subscribe LiveData
        viewModel.getDataItemList().observe(viewLifecycleOwner, Observer { list ->
            adapter?.dataList = list
            adapter?.notifyDataSetChanged()
        })
    }

}