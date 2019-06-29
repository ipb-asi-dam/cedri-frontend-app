package com.example.cedri_app.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import com.example.cedri_app.model.tables.TheseModel
import kotlinx.android.synthetic.main.my_these_item.view.*

class MyThesesAdapter(
    private val theses: List<TheseModel>,
    private val context: Context,
    private val onItemClickListener: (these: TheseModel, position: Int) -> Unit) : Adapter<MyThesesAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val these = theses[position]
        holder.bindView(these, context)
        holder.itemView.setOnClickListener {
            onItemClickListener(these, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_these_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return theses.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(these: TheseModel, context: Context) {
            val title = itemView.my_these_item_title
            val type = itemView.my_these_item_type

            val dateList = these.date.toString().split(" ")
            val year = dateList.last()


            title.text = context.resources.getString(R.string.my_these_item_title, these.title)
            type.text = context.resources.getString(R.string.my_these_item_type, these.type, year)
        }
    }
}

