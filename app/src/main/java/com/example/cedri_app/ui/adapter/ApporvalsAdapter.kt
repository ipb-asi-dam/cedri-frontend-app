package com.example.cedri_app.ui.adapter

import com.example.cedri_app.model.ApprovalProjectCard
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class ApporvalsAdapter(val activity: ApprovalsActivity) : RecyclerView.Adapter<ApporvalsAdapter.ApprovalViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ApprovalViewHolder {
        return ApprovalViewHolder(LayoutInflater.from(activity).inflate(R.layout.card_article, p0, false))
    }

    override fun getItemCount(): Int {
        return activity.articlesApprovalsList.size
    }

    override fun onBindViewHolder(p0: ApprovalViewHolder, p1: Int) {
        p0.articleTitle.text = activity.articlesApprovalsList[p1]
        p0.articleIsApproved.setImageResource(R.drawable.ic_cancel_black_24dp)

    }

    class ApprovalViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val articleTitle = v.findViewById<TextView>(R.id.cardArticle_textView)
        val articleIsApproved = v.findViewById<ImageView>(R.id.carArticle_imageView)
    }
}

