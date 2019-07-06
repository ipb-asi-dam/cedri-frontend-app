package com.example.cedri_app.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import com.example.cedri_app.model.tables.PatentModel
import kotlinx.android.synthetic.main.my_patent_item.view.*

class MyPatentsAdapter(
    private val patents: List<PatentModel>,
    private val context: Context,
    private val onItemClickListener: (patent: PatentModel, position: Int) -> Unit) : Adapter<MyPatentsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patent = patents[position]
        holder.bindView(patent, context)
        holder.itemView.setOnClickListener {
            onItemClickListener(patent, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_patent_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return patents.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(patent: PatentModel, context: Context) {
            val title = itemView.my_patent_item_title
            title.text = context.resources.getString(R.string.my_patent_item_title, patent.title)
        }
    }
}

