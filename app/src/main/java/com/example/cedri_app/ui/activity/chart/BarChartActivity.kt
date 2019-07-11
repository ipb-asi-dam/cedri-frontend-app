package com.example.cedri_app.ui.activity.chart

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.cedri_app.NetworkUtils
import com.example.cedri_app.R
import com.example.cedri_app.model.MyBarChart
import com.example.cedri_app.ui.activity.listing.ChartListActivity
import kotlinx.android.synthetic.main.activity_bar_chart.*
import kotlinx.android.synthetic.main.activity_bar_chart.logoutImageButton2
import kotlinx.android.synthetic.main.activity_chart_list.*

class BarChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        val token = NetworkUtils.getTokenFromDB(this)
        val title = intent?.extras?.getString("title") ?: ""
        NetworkUtils.setupAvatar(this, token, logoutImageButton2)

        setSupportActionBar(android.support.v7.widget.Toolbar(this))

        backImageButtonBarChart.setOnClickListener{
            val intent = Intent (this, ChartListActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }
        val menuBarTitle = when {
            title.contains("My Awards") -> "AWARDS"
            title.contains("My Publications") -> "PUBLICATIONS"
            title.contains("My Theses") -> "THESES"
            title.contains("My Projects") -> "PROJECTS"
            else -> "UNKNOWN"
        }
        this.menu_bar_title.text = this.resources.getString(
            R.string.menu_bar_titles_bar_chart_screen, menuBarTitle)
        val barChart = MyBarChart(this, token, baseContext, title)
        barChart.tryGetWorkData()
    }
}
