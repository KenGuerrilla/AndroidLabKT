package com.itl.kg.androidlabkt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itl.kg.androidlabkt.customViewLab.titleContentTextView.TitleContentTextView
import com.itl.kg.androidlabkt.databinding.ListLabItemBinding


class MainListAdapter(list: List<LabItem>)
    : RecyclerView.Adapter<MainListAdapter.LabViewHolder>() {

    private lateinit var binding: ListLabItemBinding

    private var labList: List<LabItem> = list
    var onItemOnClickListener: ItemOnClickListener? = null

    inner class LabViewHolder(
        val itemBinding: ListLabItemBinding
    ): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ListLabItemBinding.inflate(inflater, parent, false)
        return LabViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return labList.size
    }

    override fun onBindViewHolder(holder: LabViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemOnClickListener?.onItemClick(labList[position], holder.itemView)
        }

        holder.itemBinding.listLabTitleContentTextView .also {
            it.setTitle(labList[position].title)
            it.setContent(labList[position].desc)
        }
    }
}

interface ItemOnClickListener {
    fun onItemClick(labItem: LabItem, view: View)
}