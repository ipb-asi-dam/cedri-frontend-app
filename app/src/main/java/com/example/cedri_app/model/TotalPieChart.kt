package com.example.cedri_app.model

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.TextView
import android.widget.Toast
import com.example.cedri_app.*
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.text.DecimalFormat
import java.util.ArrayList

class TotalPieChart(
    val act: AppCompatActivity,
    val token: String,
    val baseContext: Context,
    val chart: Chart) {

    fun tryGetTotalWorksData() {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        when (chart.title) {
            "CeDRI Awards" -> {
                val callback = endpoint.indexTotalAwards()
                requestTotalAwardsData(callback)
            }

            "CeDRI Intellectual Properties" -> {
                val callback = endpoint.indexTotalIntellectualProperties()
                requestTotalIntellectualPropertiesData(callback)
            }

            "CeDRI Outcomes" -> {
                val callback = endpoint.indexTotalOutcomes()
                requestTotalOutcomesData(callback)
            }

            "CeDRI Publications" -> {
                val callback = endpoint.indexTotalPublications()
                requestTotalPublicationsData(callback)
            }

            "CeDRI Theses" -> {
                val callback = endpoint.indexTotalTheses()
                requestTotalThesesData(callback)
            }

            else -> {
                val callback = endpoint.indexTotalProjects()
                requestTotalProjectsData(callback)
            }
        }
    }

    private fun requestTotalAwardsData(callback: Call<AuthenticateResponse<TotalAwards>>) {
        callback.enqueue(object : Callback<AuthenticateResponse<TotalAwards>> {
            override fun onFailure(call: Call<AuthenticateResponse<TotalAwards>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<AuthenticateResponse<TotalAwards>>,
                                    response: Response<AuthenticateResponse<TotalAwards>>) {
                val responseChecker = ResponseChecker(act, response)
                if ( responseChecker.checkResponse() ) {
                    val data = response.body()?.getData() ?: run {
                        val errorMsg = "DADOS NÃO ENCONTRADOS"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    configurePieChart(data)
                }
            }
        })
    }

    private fun requestTotalIntellectualPropertiesData(callback: Call<AuthenticateResponse<TotalIntellectualProperties>>) {
        callback.enqueue(object : Callback<AuthenticateResponse<TotalIntellectualProperties>> {
            override fun onFailure(call: Call<AuthenticateResponse<TotalIntellectualProperties>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<AuthenticateResponse<TotalIntellectualProperties>>,
                                    response: Response<AuthenticateResponse<TotalIntellectualProperties>>) {
                val responseChecker = ResponseChecker(act, response)
                if ( responseChecker.checkResponse() ) {
                    val data = response.body()?.getData() ?: run {
                        val errorMsg = "DADOS NÃO ENCONTRADOS"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    configurePieChart(data)
                }
            }
        })
    }

    private fun requestTotalOutcomesData(callback: Call<AuthenticateResponse<TotalOutcomes>>) {
        callback.enqueue(object : Callback<AuthenticateResponse<TotalOutcomes>> {
            override fun onFailure(call: Call<AuthenticateResponse<TotalOutcomes>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<AuthenticateResponse<TotalOutcomes>>,
                                    response: Response<AuthenticateResponse<TotalOutcomes>>) {
                val responseChecker = ResponseChecker(act, response)
                if ( responseChecker.checkResponse() ) {
                    val data = response.body()?.getData() ?: run {
                        val errorMsg = "DADOS NÃO ENCONTRADOS"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    configurePieChart(data)
                }
            }
        })
    }

    private fun requestTotalPublicationsData(callback: Call<AuthenticateResponse<TotalPublications>>) {
        callback.enqueue(object : Callback<AuthenticateResponse<TotalPublications>> {
            override fun onFailure(call: Call<AuthenticateResponse<TotalPublications>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<AuthenticateResponse<TotalPublications>>,
                                    response: Response<AuthenticateResponse<TotalPublications>>) {
                val responseChecker = ResponseChecker(act, response)
                if ( responseChecker.checkResponse() ) {
                    val data = response.body()?.getData() ?: run {
                        val errorMsg = "DADOS NÃO ENCONTRADOS"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    configurePieChart(data)
                }
            }
        })
    }

    private fun requestTotalThesesData(callback: Call<AuthenticateResponse<TotalTheses>>) {
        callback.enqueue(object : Callback<AuthenticateResponse<TotalTheses>> {
            override fun onFailure(call: Call<AuthenticateResponse<TotalTheses>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<AuthenticateResponse<TotalTheses>>,
                                    response: Response<AuthenticateResponse<TotalTheses>>) {
                val responseChecker = ResponseChecker(act, response)
                if ( responseChecker.checkResponse() ) {
                    val data = response.body()?.getData() ?: run {
                        val errorMsg = "DADOS NÃO ENCONTRADOS"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    configurePieChart(data)
                }
            }
        })
    }

    private fun requestTotalProjectsData(callback: Call<AuthenticateResponse<TotalProjects>>) {
        callback.enqueue(object : Callback<AuthenticateResponse<TotalProjects>> {
            override fun onFailure(call: Call<AuthenticateResponse<TotalProjects>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<AuthenticateResponse<TotalProjects>>,
                                    response: Response<AuthenticateResponse<TotalProjects>>) {
                val responseChecker = ResponseChecker(act, response)
                if ( responseChecker.checkResponse() ) {
                    val data = response.body()?.getData() ?: run {
                        val errorMsg = "DADOS NÃO ENCONTRADOS"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    configurePieChart(data)
                }
            }
        })
    }


    private fun configureLegend(legend: Legend) {
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.textSize = 13f
        legend.formSize = 15f
        legend.xEntrySpace = 7f
        legend.yEntrySpace = 0f
        legend.yOffset = 0f
    }

    private fun configurePieChartSlices(pieChart: PieChart, data: Total) {
        // True para deixar em porcentagem. False para deixar o valor integral.
        pieChart.setUsePercentValues(true)

        // Array dos dados que serão inseridos no pichart
        val entry = ArrayList<PieEntry>()
        // Insere os valores nos dados do piechart
        setData(entry, data)

        // Cria o dataset de acordo com as entidades.
        val dataSet = PieDataSet(entry, "${chart.getTableName()} Results")

        // Define cores do pie chart
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        // dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        // Desenha os valores no interior das fatias do Pie Chart
        dataSet.setDrawValues(true)

        // Estiliza o conteúdo (texto) que vai ser escrito dentro das fatias
        val pieData = PieData(dataSet)
        pieData.setValueFormatter(MyPercentFormatter())
        //pieData.setValueFormatter(DefaultValueFormatter(3))
        pieData.setValueTextSize(20f)
        pieData.setValueTextColor(Color.WHITE)

        pieChart.data = pieData
    }

    private fun setupDescription() : Description {
        val desc = Description()
        desc.text = "${chart.getTableName()} Pie Chart"
        return desc
    }

    private fun <T: Total>configurePieChart(data: T) {
        val menuBarTitle = act.findViewById<TextView>(R.id.textView)
        menuBarTitle.text = chart.title
        val pieChart = act.findViewById<PieChart>(R.id.pie) ?: run {
            println("ERROR: ID do Piechart não encontrado")
            return
        }
        pieChart.setNoDataText("There is no data to display.")
        pieChart.description = setupDescription()
        configureLegend(pieChart.legend)
        val total = data.total
        pieChart.centerText = generateCenterSpannableText(total)
        configurePieChartSlices(pieChart, data)
    }

    private fun <T: Total>readAndGetDataFromJSONFile(totalClass : Class<T>) : T {
        val jFile = act.assets.open("$totalClass.json")
        val bufferedReader: BufferedReader = jFile.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        return Gson().fromJson(inputString, totalClass)
    }

    private fun setData(entry: ArrayList<PieEntry>, data: Total) {
        val pairList = data.getPairList()
        pairList.forEach {
            val (label, value) = it
            if (value > 0) {
                entry.add( PieEntry( value.toFloat(), label) )
            }
        }
    }

    private fun generateCenterSpannableText(total: Int): CharSequence {
        val msg01 = SpannableString("We've all got ")
        msg01.setSpan(RelativeSizeSpan(1.4f), 0, msg01.length, 0)

        val msg02 = SpannableString(chart.getTableName())
        msg02.setSpan(StyleSpan(Typeface.NORMAL), 0, msg02.length, 0)
        msg02.setSpan(ForegroundColorSpan(Color.RED), 0, msg02.length, 0)
        msg02.setSpan(RelativeSizeSpan(2.1f), 0, msg02.length, 0)

        val msg03 = SpannableString(".\nWe have ")
        msg03.setSpan(RelativeSizeSpan(1.4f), 0, msg03.length, 0)

        val msg04 = SpannableString("$total")
        msg04.setSpan(StyleSpan(Typeface.NORMAL), 0, msg04.length, 0)
        msg04.setSpan(ForegroundColorSpan(Color.GREEN), 0, msg04.length, 0)
        msg04.setSpan(RelativeSizeSpan(1.8f), 0, msg04.length, 0)

        val msg05 = SpannableString(" in total.")
        msg05.setSpan(RelativeSizeSpan(1.4f), 0, msg05.length, 0)

        return TextUtils.concat(msg01, msg02, msg03, msg04, msg05)
    }

    private class MyPercentFormatter : ValueFormatter() {

        var mFormat: DecimalFormat = DecimalFormat("###,###,##0.0")
        private var percentSignSeparated: Boolean = false

        init {
            percentSignSeparated = true
        }

        override fun getFormattedValue(value: Float): String {
            // return mFormat.format(value.toDouble()) + if (percentSignSeparated) " %" else "%"
            return mFormat.format(value.toDouble()) + if (percentSignSeparated) " %" else "%"
        }
    }
}