package com.itl.kg.androidlabkt

import androidx.navigation.NavDirections

/**
 *
 * Created by kenguerrilla on 2020/6/11.
 *
 */

val labList: List<LabItem> = mutableListOf(
    LabItem("CustomView", "自定義View元件範例", MainFragmentDirections.actionMainFragmentToCustomViewLab())
)

data class LabItem(val title: String, val desc: String, val nav: NavDirections)