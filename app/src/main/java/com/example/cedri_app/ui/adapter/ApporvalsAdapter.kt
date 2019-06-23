package com.example.cedri_app.ui.adapter

import com.example.cedri_app.model.ApprovalProjectCard
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import com.example.cedri_app.ApprovalsActivity
import com.example.cedri_app.R
import kotlinx.android.synthetic.main.card_article.view.*

/*
class ApporvalsAdapter(private val approvalProjectCards: List<ApprovalProjectCard>,
                       private val context: Context,
                       private val onItemClickListener: (ApprovalProjectCard: ApprovalProjectCard, position: Int) -> Unit) : Adapter<ApporvalsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val approvalProjectCard = approvalProjectCards[position]
        holder.bindView(approvalProjectCard)
        holder.itemView.setOnClickListener {
            onItemClickListener(approvalProjectCard, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_article, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return approvalProjectCards.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(ApprovalProjectCard: ApprovalProjectCard) {
            val title = itemView.cardArticle_textView
            val isApproved = itemView.carArticle_imageView
            title.text = ApprovalProjectCard.title
            isApproved.setImageResource(R.drawable.ic_check_circle_black_24dp)
        }
    }
}
*/


class ApporvalsAdapter(val activity: ApprovalsActivity, val onIntemClickListener : (project: ApprovalProjectCard, position: Int) -> Unit) : RecyclerView.Adapter<ApporvalsAdapter.ApprovalViewHolder>() {
    override fun onCreateViewHolder(view: ViewGroup, position: Int): ApprovalViewHolder {
        return ApprovalViewHolder(LayoutInflater.from(activity).inflate(R.layout.card_article, view, false))
    }

    override fun getItemCount(): Int {
        return activity.articlesApprovalsList.size
    }

    override fun onBindViewHolder(viewHolder: ApprovalViewHolder, position: Int) {
        val approvalCard = activity.articlesApprovalsList[position]

        viewHolder.itemView.setOnClickListener{
            onIntemClickListener(approvalCard, position)
        }

        viewHolder.articleTitle.text = activity.articlesApprovalsList[position].title
        if (activity.articlesApprovalsList[position].isAccepted){
            viewHolder.articleIsApproved.setImageResource(R.drawable.ic_check_circle_black_24dp)
        } else {
            viewHolder.articleIsApproved.setImageResource(R.drawable.ic_cancel_black_24dp)
        }

    }

    class ApprovalViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val articleTitle = v.findViewById<TextView>(R.id.cardArticle_textView)
        val articleIsApproved = v.findViewById<ImageView>(R.id.carArticle_imageView)
    }
}

