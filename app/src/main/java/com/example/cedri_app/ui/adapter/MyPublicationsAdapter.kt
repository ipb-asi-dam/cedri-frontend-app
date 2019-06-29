package com.example.cedri_app.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import com.example.cedri_app.model.tables.PublicationModel
import kotlinx.android.synthetic.main.publication_item.view.*

class MyPublicationsAdapter(private val publications: List<PublicationModel>,
                            private val context: Context,
                            private val onItemClickListener: (publication: PublicationModel, position: Int) -> Unit) : Adapter<MyPublicationsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val publication = publications[position]
        holder.bindView(publication, context)
        holder.itemView.setOnClickListener {
            onItemClickListener(publication, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.publication_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return publications.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(publication: PublicationModel, context: Context) {
            val title = itemView.publication_item_title
            val type = itemView.publication_item_type
            //val image = itemView.publication_item_image

            val dateList = publication.year.toString().split(" ")
            val year = dateList.last()

            title.text = publication.title
            type.text = context.resources.getString(
                R.string.my_publication_item_type, publication.type.getDisplayName(), year)
        }
    }
}

