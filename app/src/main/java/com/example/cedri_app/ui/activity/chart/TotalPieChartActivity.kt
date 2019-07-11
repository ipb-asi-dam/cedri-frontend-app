package com.example.cedri_app.ui.activity.chart

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cedri_app.*
import com.example.cedri_app.model.*
import com.example.cedri_app.ui.activity.listing.ChartListActivity
import kotlinx.android.synthetic.main.activity_total_pie_chart.*
import kotlinx.android.synthetic.main.activity_total_pie_chart.logoutImageButton2

class TotalPieChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_pie_chart)

        val token = NetworkUtils.getTokenFromDB(this)
        val title = intent?.extras?.getString("title") ?: ""
        NetworkUtils.setupAvatar(this, token, logoutImageButton2)

        val chart = Chart(title)

        val totalPieChart = TotalPieChart(this, token, baseContext, chart)
        totalPieChart.tryGetTotalWorksData()

        setSupportActionBar(android.support.v7.widget.Toolbar(this))

        backImageButtonPieChart.setOnClickListener{
            val intent = Intent (this, ChartListActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}