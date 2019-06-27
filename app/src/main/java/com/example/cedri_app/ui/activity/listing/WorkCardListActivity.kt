package com.example.cedri_app.ui.activity.listing

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.cedri_app.MenuActivity
import com.example.cedri_app.NetworkUtils
import com.example.cedri_app.R
import com.example.cedri_app.model.*
import com.example.cedri_app.model.WorkCard.WorkType.*
import com.example.cedri_app.ui.adapter.WorkCardListAdapter
import kotlinx.android.synthetic.main.activity_chart_list.backImageButtonChartList
import kotlinx.android.synthetic.main.activity_work_card_list.*

class WorkCardListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_card_list)
        val token = NetworkUtils.getToken(getIntent().getExtras())

        backImageButtonChartList.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        val recyclerView = work_card_list_recyclerview
        recyclerView.setHasFixedSize(true)

        val workCards = listOf (
            WorkCard(OUTCOMES_PUBLICATIONS, "Books, Book Chapters, Editorials, Journals and Proceedings", PublicationListActivity::class.java),
            //WorkCard(OUTCOMES_INTELLECTUAL_PROPERTIES, "Patents and Sofwares"),
            //WorkCard(OUTCOMES_THESES, "MsC and Ph.D", ),
            WorkCard(OUTCOMES_AWARDS, "Awards", MyAwardListActivity::class.java)
            //WorkCard(RESEARCH_AND_INNOVATION_PROJECTS)
        )

        recyclerView.adapter = WorkCardListAdapter(workCards, this) { workCard, position ->
            Toast.makeText(
                this,
                "Clicado no WORK CARD do recyclerView o(≧▽≦)o", Toast.LENGTH_LONG
            ).show()

            val intent = Intent(this, workCard.act)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        val layoutManager = LinearLayoutManager(this)
        //val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
}