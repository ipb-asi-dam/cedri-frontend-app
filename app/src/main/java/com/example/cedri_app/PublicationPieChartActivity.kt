package com.example.cedri_app

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageButton
import android.widget.Toast
import com.example.cedri_app.model.CountPublications
import com.example.cedri_app.model.CountPublicationsChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_publication_pie_chart.*
import kotlinx.android.synthetic.main.activity_test_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.util.*

class PublicationPieChartActivity : AppCompatActivity() {

    var mPie: PieChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication_pie_chart)

        setSupportActionBar(android.support.v7.widget.Toolbar(this))

        backImageButtonPieChart.setOnClickListener{
            val intent = Intent (this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        //getData()
        setupPieChartView()
    }

    fun getData() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("https://jsonplaceholder.typicode.com")

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getPosts()

        callback.enqueue(object: Callback<List<Posts>> {
            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                setupPieChartView()
            }
        })
    }

    fun readJSONfromFile() : CountPublications {
        var gson = Gson()
        val jFile = assets.open("publications.json")
        val bufferedReader: BufferedReader = jFile.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        var countPublications = gson.fromJson(inputString, CountPublicationsChart::class.java)

        return countPublications.publications
    }

    fun setupPieChartView() {

        mPie = findViewById(R.id.pie)

        mPie?.setUsePercentValues(true)

        val desc: Description = Description()
        desc.text = "PieChart das Publicações"
        mPie?.description = desc

        val legend: Legend? = mPie?.legend
        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT

        val entry = ArrayList<PieEntry>()
        val publications: CountPublications = readJSONfromFile()
        entry.add( PieEntry( publications.book.toFloat(), "book"))
        entry.add( PieEntry( publications.bookChapter.toFloat(), "bookChapter"))
        entry.add( PieEntry( publications.editorial.toFloat(), "editorial"))
        entry.add( PieEntry( publications.proceeding.toFloat(), "proceeding"))
        entry.add( PieEntry( publications.journal.toFloat(), "journal"))

        val dataSet = PieDataSet(entry, "Rótulos")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        dataSet.setDrawValues(true)
        val pieData = PieData(dataSet)
        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextSize(20f)
        pieData.setValueTextColor(Color.WHITE)
        mPie?.data = pieData
    }
}