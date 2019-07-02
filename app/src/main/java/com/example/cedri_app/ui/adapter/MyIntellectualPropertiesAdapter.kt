package com.example.cedri_app.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import com.example.cedri_app.model.tables.IntellectualPropertyModel
import kotlinx.android.synthetic.main.my_intellectual_property_item.view.*

class MyIntellectualPropertiesAdapter(
    private val intellectualProperties: List<IntellectualPropertyModel>,
    private val context: Context,
    private val onItemClickListener: (intellectualProperty: IntellectualPropertyModel, position: Int) -> Unit) : Adapter<MyIntellectualPropertiesAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val intellectualProperty = intellectualProperties[position]
        holder.bindView(intellectualProperty, context)
        holder.itemView.setOnClickListener {
            onItemClickListener(intellectualProperty, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_intellectual_property_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return intellectualProperties.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(intellectualProperty: IntellectualPropertyModel, context: Context) {
            val title = itemView.my_intellectual_property_item_title
            title.text = context.resources.getString(R.string.my_intellectual_property_item_title, intellectualProperty.title)

            val type = itemView.my_intellectual_property_item_type
            if (intellectualProperty.authors != null) {
                 type.text = context.resources.getString(R.string.my_intellectual_property_item_type, "Patent")
            } else {
                type.text = context.resources.getString(R.string.my_intellectual_property_item_type, "Software")
            }
        }
    }
}

