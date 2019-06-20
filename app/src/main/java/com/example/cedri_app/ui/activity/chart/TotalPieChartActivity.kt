package com.example.cedri_app.ui.activity.chart

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.Toast
import com.example.cedri_app.*
import com.example.cedri_app.model.*
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_publication_pie_chart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.util.*
import java.text.DecimalFormat

class TotalPieChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication_pie_chart)

        /* Start main process */

        val token = NetworkUtils.getToken(getIntent().getExtras())
        val extras = getIntent().getExtras()
        val title = extras?.getString("title")

        var totalClass = when {
            title == "Total Awards" -> TotalOutcomes::class.java
            title == "Total Intellectual Properties" -> TotalIntellectualProperties::class.java
            title == "Total Outcomes" -> TotalOutcomes::class.java
            title == "Total Publications" -> TotalPublications::class.java
            title == "Total Theses" -> TotalTheses::class.java
            else -> TotalOutcomes::class.java
        }

        var cls = when {
            title == "Total Awards" -> TotalOutcomesEndpoint::class.java
            title == "Total Intellectual Properties" -> TotalIntellectualPropertiesEndpoint::class.java
            title == "Total Outcomes" -> TotalOutcomesEndpoint::class.java
            title == "Total Publications" -> TotalPublicationsEndpoint::class.java
            title == "Total Theses" -> TotalThesesEndpoint::class.java
            else -> TotalOutcomesEndpoint::class.java
        }

        val totalPieChart = TotalPieChart(
            this,
            token,
            baseContext,
            totalClass,
            cls)
        totalPieChart.tryGetData()
        //tryGetData(this, token)

        /* End main process */

        /* Start process for test, get outcomes data from files. */
        //configureOutcomesChart( readAndGetOutcomesJSONFile() )
        /* End process for test. */

        setSupportActionBar(android.support.v7.widget.Toolbar(this))

        backImageButtonPieChart.setOnClickListener{
            val intent = Intent (this, ChartListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}