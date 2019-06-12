package com.example.cedri_app

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.Toast
import com.example.cedri_app.model.TotalPublications
import com.example.cedri_app.model.TotalOutcomes
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_publication_pie_chart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.util.*
import com.github.mikephil.charting.utils.ViewPortHandler
import com.github.mikephil.charting.formatter.IValueFormatter
import java.text.DecimalFormat


class OutcomesChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication_pie_chart)

        setSupportActionBar(android.support.v7.widget.Toolbar(this))

        backImageButtonPieChart.setOnClickListener{
            val intent = Intent (this, ChartListActivity::class.java)
            startActivity(intent)
            finish()
        }
        val a = AppCompatActivity()

        //getOutcomesData()
        setupPieChartView()
        //setupPieChartView2()

    }
    /*
    // oncereate-> onStart-> onResume -> ... CICLO DE VIDA DA ACTIVITY
    override fun onStart() {
        super.onStart()
        //getData()
    }*/

    fun getOutcomesData() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("https://jsonplaceholder.typicode.com")
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getPosts()
        // Asynchronous request. For synchronous request, use callback.execute()
        callback.enqueue(object: Callback<List<Posts>> {
            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                setupPieChartView()
            }
        })
    }

    fun readJSONfromFile() : TotalPublications {
        var gson = Gson()
        val jFile = assets.open("total_outcomes.json")
        val bufferedReader: BufferedReader = jFile.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        var countPublications = gson.fromJson(inputString, TotalOutcomes::class.java)

        return countPublications.publications
    }

    fun setData(entry: ArrayList<PieEntry>) {
        val publications: TotalPublications = readJSONfromFile()

        entry.add( PieEntry( publications.book.toFloat(), "Book"))
        entry.add( PieEntry( publications.bookChapter.toFloat(), "bookChapter"))
        entry.add( PieEntry( publications.editorial.toFloat(), "editorial"))
        entry.add( PieEntry( publications.proceeding.toFloat(), "proceeding"))
        entry.add( PieEntry( publications.journal.toFloat(), "journal"))

        println("PUBLICATION BOOK> ${publications.book.toFloat()}")
    }

    fun setupPieChartView() {
        var mPie: PieChart? = null
        mPie = findViewById(R.id.pie)

        mPie?.setUsePercentValues(true)

        val desc: Description = Description()
        //desc.text = "PieChart das Publicações"

        mPie?.description = desc
        val legend: Legend? = mPie?.legend

        legend?.let {
            it.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            it.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            it.orientation = Legend.LegendOrientation.VERTICAL
            it.setDrawInside(false)
            it.textSize = 13f
            it.formSize = 15f
            it.setXEntrySpace(7f)
            it.setYEntrySpace(0f)
            it.setYOffset(0f)
        }
        mPie.setCenterText(generateCenterSpannableText(132))
        val entry = ArrayList<PieEntry>()
        setData(entry)

        // Insere os textos
        val dataSet = PieDataSet(entry, "Rótulos")

        // Define cores do pie chart
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        // dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        // Insere os valores
        dataSet.setDrawValues(true)
        val pieData = PieData(dataSet)
        pieData.setValueFormatter(MyPercentFormatter())
        //pieData.setValueFormatter(DefaultValueFormatter(3))
        pieData.setValueTextSize(20f)
        pieData.setValueTextColor(Color.WHITE)

        mPie?.data = pieData
    }

    private fun generateCenterSpannableText(total: Int): SpannableString {
        val digits = total.toString().length // 14 ~ 22
        val s = SpannableString("We've all got Outcomes.\nWe have ${total} in total.")

        s.setSpan(RelativeSizeSpan(1.5f), 0, 14, 0)

        s.setSpan(StyleSpan(Typeface.NORMAL), 14, 22, 0)
        s.setSpan(ForegroundColorSpan(Color.RED), 14, 22, 0)
        s.setSpan(RelativeSizeSpan(2.1f), 14, 22, 0)

        s.setSpan(RelativeSizeSpan(1.5f), 22, 27, 0)

        s.setSpan(StyleSpan(Typeface.NORMAL), 32, 32+digits, 0)
        s.setSpan(ForegroundColorSpan(Color.GREEN), 32, 32+digits, 0)
        s.setSpan(RelativeSizeSpan(1.8f), 32, 32+digits, 0)

        s.setSpan(RelativeSizeSpan(1.5f), 32+digits, s.length, 0)


        return s
    }

    fun setupPieChartView2() {
        var mPie: PieChart? = null
        mPie = findViewById(R.id.pie)

        mPie?.setUsePercentValues(true)

        val desc: Description = Description()
        desc.text = "PieChart das Publicações"
        mPie?.description = desc
        val legend: Legend? = mPie?.legend

        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT


        val entry = ArrayList<PieEntry>()

        val publications: TotalPublications = readJSONfromFile()

        entry.add( PieEntry( publications.book.toFloat(), "Book (${publications.book})"))
        println("PUBLICATION BOOK> ${publications.book.toFloat()}")
        entry.add( PieEntry( publications.bookChapter.toFloat(), "BookChapter"))
        entry.add( PieEntry( publications.editorial.toFloat(), "Editorial"))
        entry.add( PieEntry( publications.proceeding.toFloat(), "Proceeding"))
        entry.add( PieEntry( publications.journal.toFloat(), "Journal"))
        val dataSet = PieDataSet(entry, "Rótulos")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        dataSet.setDrawValues(true)
        val pieData = PieData(dataSet)
        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextSize(20f)
        pieData.setValueTextColor(Color.WHITE)
        mPie?.data = pieData
    }


    private class MyPercentFormatter() : ValueFormatter() {

        var mFormat: DecimalFormat
        private var percentSignSeparated: Boolean = false

        init {
            mFormat = DecimalFormat("###,###,##0.0")
            percentSignSeparated = true
        }

        override fun getFormattedValue(value: Float): String {
            // return mFormat.format(value.toDouble()) + if (percentSignSeparated) " %" else "%"
            return mFormat.format(value.toDouble()) + if (percentSignSeparated) " %" else "%"
        }

    }
}