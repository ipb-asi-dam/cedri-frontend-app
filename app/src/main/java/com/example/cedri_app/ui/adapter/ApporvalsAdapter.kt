package com.example.cedri_app.ui.adapter

import com.example.cedri_app.model.ApprovalProjectCard
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cedri_app.ApprovalsActivity
import com.example.cedri_app.R

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

