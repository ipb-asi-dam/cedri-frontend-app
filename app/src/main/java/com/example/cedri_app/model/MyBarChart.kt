package com.example.cedri_app.model

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.cedri_app.*
import com.example.cedri_app.model.response.AnnualItem
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.graphics.Typeface
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.DecimalFormat

class MyBarChart(
    val act: AppCompatActivity,
    val token: String,
    val baseContext: Context,
    val title: String) {

    fun tryGetWorkData() {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        when (title) {
            "My Awards" -> requestData( endpoint.list5YearsAwardData(currentYear-5, currentYear-1) )
            "My Publications" -> requestData( endpoint.list5YearsPublicationData(currentYear-5, currentYear-1) )
            "My Theses" -> requestData( endpoint.list5YearsTheseData(currentYear-5, currentYear-1) )
            else -> requestData( endpoint.list5YearsProjectData(currentYear-5, currentYear-1) )
        }
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
            return
        }
        val xAxisValues = data.map {it.year.toString()}

        barChart.description.isEnabled = false

        barChart.animateY(1500)
        barChart.legend.isEnabled = false

        val entry : MutableList<BarEntry> = mutableListOf()
        setData(entry, data)
        val set = BarDataSet(entry, "Years")
        set.setColors(*ColorTemplate.JOYFUL_COLORS)
        set.valueTextColor = Color.rgb(55, 70, 73)
        set.valueTextSize = 10f
        set.valueFormatter

        val barData = BarData(set)
        barChart.data = barData
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisLeft.textSize = 20f

        barChart.description.isEnabled = false

        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.setPinchZoom(false)

        val xAxis = barChart.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(false)

        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.TOP
        xAxis.axisMaximum = 50f

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        barChart.setExtraOffsets(2f, 25f, 15f, 20f)
        val leftAxis = barChart.axisLeft
        leftAxis.removeAllLimitLines()
        leftAxis.typeface = Typeface.DEFAULT
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.textColor = Color.BLACK
        leftAxis.setDrawGridLines(true)
        barChart.axisRight.isEnabled = false
        barData.barWidth = 0.85f
        barData.setValueTextSize(22f)
        barData.setValueFormatter(MyIntegerFormatter())
        barChart.xAxis.axisMinimum = -0.5f
        barChart.xAxis.axisMaximum = 4.5f
        barChart.setDrawGridBackground(true)

        barChart.xAxis.textSize = 20f
        barChart.invalidate()

    }
    private fun setData(entry: MutableList<BarEntry>, data: List<AnnualItem>) {
        var count = 0
        data.forEach {
            entry.add( BarEntry( count.toFloat(), it.qty.toFloat() ) )
            count++
        }
    }

    private class MyIntegerFormatter : ValueFormatter() {
        var mFormat: DecimalFormat = DecimalFormat("###,###,##0")
        override fun getFormattedValue(value: Float): String {
            // return mFormat.format(value.toDouble()) + if (percentSignSeparated) " %" else "%"
            return mFormat.format(value.toInt())
        }
    }
}