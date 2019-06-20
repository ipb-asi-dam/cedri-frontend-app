package com.example.cedri_app

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.cedri_app.model.*
import kotlinx.android.synthetic.main.activity_chart_list.*
import com.example.cedri_app.ui.activity.chart.OutcomesChartActivity
import com.example.cedri_app.ui.activity.chart.TotalPieChartActivity
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

        recyclerView.adapter = ChartListAdapter(charts(), this) { chart, position ->
            val tes = chart
            Toast.makeText(
                this,
                "Clicando no item do recyclerView o(≧▽≦)o", Toast.LENGTH_LONG
            ).show()

            val act = when {
                chart.title == "Total Awards" -> TotalPieChartActivity::class.java
                chart.title == "Total Intellectual Properties" -> TotalPieChartActivity::class.java
                chart.title == "Total Outcomes" -> TotalPieChartActivity::class.java
                chart.title == "Total Publications" -> TotalPieChartActivity::class.java
                chart.title == "Total Theses" -> TotalPieChartActivity::class.java
                else -> TotalPieChartActivity::class.java
            }

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

    private fun charts(): List<Chart<out Total, *>> {
        return listOf(
            Chart("Total Awards", "Unknown", TotalOutcomes::class.java, TotalOutcomesEndpoint::class.java),
            Chart("Total Intellectual Properties", "Pie Chart", TotalIntellectualProperties::class.java, TotalIntellectualPropertiesEndpoint::class.java),
            Chart("Total Outcomes", "Pie Chart", TotalOutcomes::class.java, TotalOutcomesEndpoint::class.java),
            Chart("Total Publications", "Pie Chart", TotalPublications::class.java, TotalPublicationsEndpoint::class.java),
            Chart("Total Theses", "Pie Chart", TotalTheses::class.java, TotalThesesEndpoint::class.java)
        )
    }
}