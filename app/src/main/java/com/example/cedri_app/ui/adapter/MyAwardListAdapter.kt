package com.example.cedri_app.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import com.example.cedri_app.model.tables.AwardModel
import kotlinx.android.synthetic.main.my_award_item.view.*

class MyAwardListAdapter(
    private val awards: List<AwardModel>,
    private val context: Context,
    private val onItemClickListener: (award: AwardModel, position: Int) -> Unit) : Adapter<MyAwardListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val award = awards[position]
        holder.bindView(award, context)
        holder.itemView.setOnClickListener {
            onItemClickListener(award, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_award_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return awards.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(award: AwardModel, context: Context) {
            val title = itemView.my_award_item_title
            val event = itemView.my_award_item_event
            title.text = context.resources.getString(R.string.my_award_item_title, award.title)
            event.text = context.resources.getString(R.string.my_award_item_event, "unknown", "2019")
        }
    }
}

