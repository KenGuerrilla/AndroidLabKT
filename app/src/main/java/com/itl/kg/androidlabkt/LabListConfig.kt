package com.itl.kg.androidlabkt

import androidx.navigation.NavDirections

/**
 *
 * NavDirections為Navigation components物件，記錄畫面切換的目的
 *
 */

val labList: List<LabItem> = mutableListOf(
    LabItem("CustomView", "自定義View元件範例", MainFragmentDirections.actionMainFragmentToCustomViewLab()),
    LabItem("RoomLab", "Room與MVVM示範", MainFragmentDirections.actionMainFragmentToRoomLabFragment()),
    LabItem("BottomSheetDialogFragmentLab", "BottomSheet範例", MainFragmentDirections.actionMainFragmentToBottomSheetLabFragment()),
    LabItem("LayoutBehaviorLab", "Layout Behavior連動示範", MainFragmentDirections.actionMainFragmentToLayoutBehaviorLabFragment())
)

data class LabItem(val title: String, val desc: String, val nav: NavDirections)