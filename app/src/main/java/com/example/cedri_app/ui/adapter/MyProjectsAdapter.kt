package com.example.cedri_app.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import com.example.cedri_app.model.tables.ProjectModel
import kotlinx.android.synthetic.main.my_project_item.view.*

class MyProjectsAdapter(
    private val projects: List<ProjectModel>,
    private val context: Context,
    private val onItemClickListener: (project: ProjectModel, position: Int) -> Unit) : Adapter<MyProjectsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        holder.bindView(project, context)
        holder.itemView.setOnClickListener {
            onItemClickListener(project, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_project_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(project: ProjectModel, context: Context) {
            val title = itemView.my_project_item_title
            val type = itemView.my_project_item_type

            val startDateList = project.startDate.toString().split(" ")
            val startYear = startDateList.last()

            val endDateList = project.endDate.toString().split(" ")
            val endYear = endDateList.last()

            title.text = context.resources.getString(R.string.my_project_item_title, project.title)
            type.text = context.resources.getString(R.string.my_project_item_type, project.type, startYear, endYear)
        }
    }
}

