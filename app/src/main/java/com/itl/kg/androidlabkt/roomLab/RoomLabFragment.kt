package com.itl.kg.androidlabkt.roomLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.itl.kg.androidlabkt.databinding.FragmentRoomLabBinding
import com.itl.kg.androidlabkt.roomLab.data.RoomLabDataItem
import com.itl.kg.androidlabkt.roomLab.utilities.InjectorUtils
import com.itl.kg.androidlabkt.roomLab.viewModel.RoomLabDataViewModel

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
 * Room 主要由下列三個Components組成，詳細說明請參照Lab中data package各物件說明
 *  a. Database
 *  b. Entity
 *  c. Data Access Objects
 *
 *
 * 相關說明如下
 *  1. MVVM用於畫面資料的更新，可查看viewModel package相關物件
 *  2. Room則實作於Repository，可查看data package相關物件
 *  3. ViewModel的部分則是使用委託的方式交給androidx.fragment.app.Fragment進行實作，並由InjectorUtils取得注入性依賴的ViewModelFactory實作
 *  4. 由於使用kotlin-android-extensions的關係，取得View物件應在畫面建立之後( onViewCreated )，否則會產生NPE的問題
 *  5. Demo是使用Kotlin，所以要在Gradle app dependencies 加上 kapt "androidx.room:room-compiler:_版本號_"，否則無法產生相對應的物件實作
 *
 *
 *
 * 注意事項
 *  1. 注意LiveData subscribe如果與View有關連，應該在View建立完成後再實作，否則會有NPE的問題
 *  2. 因為使用Kotlin Android Extension的功能來取得View元件，其原理為使用getView()方法，所以View元件的Data初始化應實作於View被建立之後，
 *     因此這裡實作於onViewCreated()
 *
 *
 *
 * 參考資料
 * MVVM - https://developer.android.com/jetpack/docs/guide
 * Room - https://developer.android.com/training/data-storage/room
 * Sunflower project - https://github.com/android/sunflower/
 *
 */

class RoomLabFragment : Fragment() {

    private var _binding: FragmentRoomLabBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RoomLabListAdapter

    private val viewModel: RoomLabDataViewModel by viewModels {
        InjectorUtils.provideRoomLabDataViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoomLabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // View物件參數的初始化應實作晚於或此處，早於onViewCreated則會有NPE的問題
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeUi()
    }

    private fun initView() {
        binding.roomLabAddButton.setOnClickListener {
            createDataItem()
        }
        initRecyclerView()
    }

    private fun createDataItem() {
        if (binding.roomLabEditText.text.isBlank()) {
            Toast.makeText(requireContext(), "Message was blank", Toast.LENGTH_SHORT).show()
        } else {
            val item = RoomLabDataItem(binding.roomLabEditText.text.toString())
            viewModel.addDataItem(item)
            binding.roomLabEditText.editableText.clear()
        }
    }

    private fun initRecyclerView() {
        adapter = RoomLabListAdapter(listOf())

        adapter.itemClickListener = object : RoomLabDataListAdapterOnItemClickListener {
            override fun onItemClick(item: RoomLabDataItem, position: Int) {
                // update item time
                viewModel.updateDateItem(item)
            }
        }

        adapter.itemLongClickListener = object : RoomLabDataListAdapterOnItemLongClickListener {
            override fun onItemLongClick(item: RoomLabDataItem, position: Int): Boolean {
                viewModel.deleteItem(item)
                return true
            }
        }

        // Init RecyclerView
        binding.roomLabRecyclerView.also {
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
            adapter.dataList = list
            adapter.notifyDataSetChanged()
        })
    }

}