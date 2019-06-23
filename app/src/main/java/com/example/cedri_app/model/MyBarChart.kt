package com.example.cedri_app.model

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.cedri_app.*
import com.example.cedri_app.model.response.AnnualItem
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MyBarChart(
    val act: AppCompatActivity,
    val token: String,
    val baseContext: Context,
    val title: String) {

    fun tryGetWorkData() {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val work = when (title) {
            "My Awards" -> "award"
            "My Intellectual Properties" -> "intellectual_properties"
            "My Outcomes" -> "outcomes"
            "My Publications" -> "publication"
            "My Theses" -> "these"
            "MY Projects" -> "project"
            else -> "outcomes"
        }
        val callback = endpoint.indexAnnual(work)
        requestData(callback)
    }

    private fun requestData(
        callback : Call<AuthenticateResponse<List<AnnualItem>>>) {
        callback.enqueue(object : Callback<AuthenticateResponse<List<AnnualItem>>> {

            override fun onFailure(
                call: Call<AuthenticateResponse<List<AnnualItem>>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<AuthenticateResponse<List<AnnualItem>>>,
                response: Response<AuthenticateResponse<List<AnnualItem>>>) {
                val responseChecker = ResponseChecker(act, response)

                if ( responseChecker.checkResponse() ) {
                    val data = response.body()?.getData() ?: run {
                        val errorMsg = "DADOS NÃO ENCONTRADOS"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    configureBarChart(data)
                }
            }
        })
    }

    private fun configureBarChart(data: List<AnnualItem>) {
        val barChart: BarChart = act.findViewById(R.id.chart1) ?: run {
            println("ERROR: ID do Bar Chart não encontrado")
            return
        }

        val xAxisValues = data.map {it.year.toString()}
        barChart.description.isEnabled = false

        barChart.setMaxVisibleValueCount(60)
        barChart.setPinchZoom(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawGridBackground(true)

        //chart.xAxis.position = XAxisPosition.BOTTOM
        //chart.xAxis.setDrawGridLines(true)

        //chart.axisLeft.setDrawGridLines(true)
        //chart.axisRight.setDrawGridLines(true)

        barChart.animateY(1500)
        barChart.legend.isEnabled = false

        val entry = ArrayList<BarEntry>()
        setData(entry, data)

        val dataSet = BarDataSet(entry, "Data Set")
        dataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        dataSet.setDrawValues(true)

        val barData = BarData(dataSet)
        barChart.setData(barData)
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        barChart.setFitBars(true)
    }
    private fun setData(entry: ArrayList<BarEntry>, data: List<AnnualItem>) {
        var count = 0
        data.forEach {
            entry.add( BarEntry( it.qty.toFloat(), count.toFloat()) )
            count++
        }
    }

}