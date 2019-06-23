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
import kotlinx.android.synthetic.main.activity_chart_list.*
import com.example.cedri_app.ui.adapter.ChartListAdapter
import kotlinx.android.synthetic.main.activity_chart_list.backImageButtonChartList

class ChartListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_list)
        val token = NetworkUtils.getToken(getIntent().getExtras())

        backImageButtonChartList.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        val recyclerView = chart_list_recyclerview
        recyclerView.setHasFixedSize(true)

        val charts = ChartList(true).getCharts()
        recyclerView.adapter = ChartListAdapter(charts, this) { chart, position ->
            Toast.makeText(
                this,
                "Clicando no item do recyclerView o(≧▽≦)o", Toast.LENGTH_LONG
            ).show()

            val act = chart.getChartActivity()

            val intent = Intent(this, act)
            intent.putExtra("title", chart.title)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        val layoutManager = LinearLayoutManager(this)
        //val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
}