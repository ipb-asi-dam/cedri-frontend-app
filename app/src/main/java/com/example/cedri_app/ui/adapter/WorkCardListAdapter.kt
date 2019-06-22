package com.example.cedri_app.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import com.example.cedri_app.model.WorkCard
import kotlinx.android.synthetic.main.work_card_item.view.*

class WorkCardListAdapter(
    private val workCards : List<WorkCard>,
    private val context: Context,
    private val onItemClickListener: (workCard: WorkCard, position: Int) -> Unit) : Adapter<WorkCardListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workCard = workCards[position]
        holder.bindView(workCard)
        holder.itemView.setOnClickListener {
            onItemClickListener(workCard, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.work_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() : Int {
        return workCards.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(workCard: WorkCard) {
            val type = itemView.work_card_item_type
            val contents = itemView.work_card_item_contents
            //val image = itemView.work_card_item_image
            type.text = workCard.type.getDisplayName()
            contents.text = workCard.contents
            //image.setImageResource(R.drawable.piechart_2_icon2)
        }
    }
}