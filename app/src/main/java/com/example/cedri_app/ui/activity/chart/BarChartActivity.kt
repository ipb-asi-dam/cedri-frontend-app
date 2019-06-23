package com.example.cedri_app.ui.activity.chart

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.cedri_app.NetworkUtils
import com.example.cedri_app.R
import com.example.cedri_app.model.MyBarChart
import com.example.cedri_app.ui.activity.listing.ChartListActivity
import kotlinx.android.synthetic.main.activity_bar_chart.*

class BarChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        val token = NetworkUtils.getToken(intent.extras)
        val title = intent?.extras?.getString("title") ?: ""

        setSupportActionBar(android.support.v7.widget.Toolbar(this))
        backImageButtonBarChart.setOnClickListener{
            val intent = Intent (this, ChartListActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        this.menu_bar_title.text = this.resources.getString(
            R.string.menu_bar_titles_bar_chart_screen, "PUBLICATIONS")
        val barChart = MyBarChart(this, token, baseContext, title)
        barChart.tryGetWorkData()
    }
}
