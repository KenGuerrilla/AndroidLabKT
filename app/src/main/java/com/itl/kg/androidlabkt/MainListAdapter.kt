package com.itl.kg.androidlabkt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itl.kg.androidlabkt.customViewLab.titleContentTextView.TitleContentTextView

/**
 *
 * Created by kenguerrilla on 2020/6/10.
 *
 * 實作List Item
 *
 */
class MainListAdapter(list: List<LabItem>)
    : RecyclerView.Adapter<MainListAdapter.LabViewHolder>() {

    var labList: List<LabItem> = list
    var onItemOnClickListener: ItemOnClickListener? = null

    inner class LabViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cvTextVIew: TitleContentTextView = view.findViewById(R.id.list_lab_titleContentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_lab_item, parent, false)
        return LabViewHolder(view)
    }

    override fun getItemCount(): Int {
        return labList.size
    }

    override fun onBindViewHolder(holder: LabViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemOnClickListener?.onItemClick(labList[position], holder.itemView)
        }

        holder.cvTextVIew.also {
            it.setTitle(labList[position].title)
            it.setContent(labList[position].desc)
        }
    }
}

interface ItemOnClickListener {
    fun onItemClick(labItem: LabItem, view: View)
}