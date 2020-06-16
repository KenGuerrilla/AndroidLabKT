package com.itl.kg.androidlabkt.roomLab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itl.kg.androidlabkt.roomLab.data.RoomLabDataItem

/**
 *
 * Created by kenguerrilla on 2020/6/12.
 *
 */

class RoomLabListAdapter (list: List<RoomLabDataItem>)
    : RecyclerView.Adapter<RoomLabListAdapter.RoomLabViewHolder>() {

    var dataList = list

    var itemClickListener: RoomLabDataListAdapterOnItemClickListener? = null
    var itemLongClickListener: RoomLabDataListAdapterOnItemLongClickListener? = null

    inner class RoomLabViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvContent: TextView = view.findViewById(android.R.id.text1)
        val tvCreateDate: TextView = view.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomLabViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return RoomLabViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RoomLabViewHolder, position: Int) {

        holder.tvContent.text = dataList[position].text
        holder.tvCreateDate.text = dataList[position].createTime.time.toString()

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(dataList[position], position)
        }

        holder.itemView.setOnLongClickListener {
            itemLongClickListener?.onItemLongClick(dataList[position], position) ?: false
        }
    }
}

interface RoomLabDataListAdapterOnItemClickListener {
    fun onItemClick(item: RoomLabDataItem, position: Int)
}

interface RoomLabDataListAdapterOnItemLongClickListener {
    fun onItemLongClick(item: RoomLabDataItem, position: Int): Boolean
}