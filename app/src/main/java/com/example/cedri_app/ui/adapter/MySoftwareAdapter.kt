package com.example.cedri_app.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import com.example.cedri_app.model.tables.SoftwareModel
import kotlinx.android.synthetic.main.my_software_item.view.*

class MySoftwareAdapter(
    private val softwareList: List<SoftwareModel>,
    private val context: Context,
    private val onItemClickListener: (software: SoftwareModel, position: Int) -> Unit) : Adapter<MySoftwareAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val software = softwareList[position]
        holder.bindView(software, context)
        holder.itemView.setOnClickListener {
            onItemClickListener(software, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_software_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return softwareList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(software: SoftwareModel, context: Context) {
            val title = itemView.my_software_item_title
            title.text = context.resources.getString(R.string.my_software_item_title, software.title)
        }
    }
}

