package com.example.cedri_app.ui.adapter

import com.example.cedri_app.model.Chart
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import kotlinx.android.synthetic.main.chart_item.view.*

class ChartListAdapter(
    private val charts: List<Chart>,
    private val context: Context,
    private val onItemClickListener: (chart: Chart, position: Int) -> Unit) : Adapter<ChartListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chart = charts[position]
        holder.bindView(chart)
        holder.itemView.setOnClickListener {
            onItemClickListener(chart, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chart_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return charts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(chart: Chart) {
            val title = itemView.chart_item_title
            val description = itemView.chart_item_description
            val image = itemView.chart_item_image
            title.text = chart.title
            description.text = chart.getDescription()
            when {
                (chart.title.contains("CeDRI")) -> image.setImageResource(R.drawable.piechart_2_icon2)
                (chart.title.contains("My")) -> image.setImageResource(R.drawable.bar_chart_scaled)
                else -> image.setImageResource(R.drawable.piechart_2_icon2)
            }
        }
    }
}

