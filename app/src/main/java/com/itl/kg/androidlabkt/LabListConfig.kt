package com.itl.kg.androidlabkt

import androidx.navigation.NavDirections

/**
 *
 * NavDirections為Navigation components物件，記錄畫面切換的目的
 *
 */

val labList: List<LabItem> = mutableListOf(
    LabItem("CustomView", "自定義View元件範例", MainFragmentDirections.actionMainFragmentToCustomViewLab()),
    LabItem("RoomLab", "Room與MVVM示範", MainFragmentDirections.actionMainFragmentToRoomLabFragment())
)

data class LabItem(val title: String, val desc: String, val nav: NavDirections)