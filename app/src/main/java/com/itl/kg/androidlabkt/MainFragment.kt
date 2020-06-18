package com.itl.kg.androidlabkt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*

/**
 *
 * AndroidLabKT專案運作方法說明如下
 *  1. MainFragment使用Navigation Component管理個畫面之間的切換作業
 *  2. 透過LabListConfig提供LabItem物件給RecyclerView顯示
 *  3. 由Adapter的OnItemClickListener提供指定Position的LabItem物件
 *  4. 透過LabItem物件中的NavDirections丟給Navigator.setupAction()方法進行畫面切換
 *
 * 備註
 * a. __name__Directions 物件由編譯器產生，關鍵字 androidx.navigation.safeargs.kotlin
 *
 */

class MainFragment : Fragment() {

    private lateinit var adapter: MainListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        adapter = MainListAdapter(labList).also {
            it.onItemOnClickListener = object: ItemOnClickListener {
                override fun onItemClick(labItem: LabItem, view: View) {
                    Navigator.setupAction(labItem.nav, view)
                }
            }
        }

        mainFragment_recyclerView.also {
            it.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
        }
    }

}