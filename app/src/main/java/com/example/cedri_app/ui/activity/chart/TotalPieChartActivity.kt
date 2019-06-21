package com.example.cedri_app.ui.activity.chart

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cedri_app.*
import com.example.cedri_app.model.*
import kotlinx.android.synthetic.main.activity_total_pie_chart.*

class TotalPieChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_pie_chart)

        val token = NetworkUtils.getToken(getIntent().getExtras())
        val extras = getIntent().getExtras()
        val title = extras?.getString("title") ?: ""
        val chart = Chart(title)

        val totalPieChart = TotalPieChart(this, token, baseContext, chart)
        totalPieChart.tryGetData()

        setSupportActionBar(android.support.v7.widget.Toolbar(this))

        backImageButtonPieChart.setOnClickListener{
            val intent = Intent (this, ChartListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}