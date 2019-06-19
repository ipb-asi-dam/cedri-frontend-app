package com.example.cedri_app

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
import android.widget.TextView
import android.widget.Toast
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

class OutcomesChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication_pie_chart)

        /* Start main process */
        /*
        val token = NetworkUtils.getToken(getIntent().getExtras())
        tryGetData(this, token)
        */
        /* End main process */

        /* Start process for test, get outcomes data from files. */
        configureOutcomesChart( readAndGetOutcomesJSONFile() )
        /* End process for test. */

        setSupportActionBar(android.support.v7.widget.Toolbar(this))

        backImageButtonPieChart.setOnClickListener{
            val intent = Intent (this, ChartListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun tryGetData(mainAct : OutcomesChartActivity, token : String) {
        val tokenInterceptor = TokenInterceptor(token)

        val retrofitClient = NetworkUtils.setupRetrofit(tokenInterceptor, NetworkUtils.getBaseUrl())
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.showTotalOutcomesShow()

        // Asynchronous request. For synchronous request, use callback.execute()
        callback.enqueue(object : Callback<AuthenticateResponse<TotalOutcomes>> {
            override fun onFailure(call: Call<AuthenticateResponse<TotalOutcomes>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<TotalOutcomes>>, response: Response<AuthenticateResponse<TotalOutcomes>>) {
                val responseChecker = ResponseChecker(mainAct, response)

                if ( responseChecker.checkResponse() ) {
                    val totalOutcomes : TotalOutcomes = response.body()?.getData() ?: run {
                        val errorMsg = "OUTCOMES NÃO ENCONTRADO"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    configureOutcomesChart(totalOutcomes)
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

    private fun configurePieChartSlices(mPie: PieChart, totalOutcomes: TotalOutcomes) {
        // True para deixar em porcentagem. False para deixar o valor integral.
        mPie.setUsePercentValues(true)

        // Array dos dados que serão inseridos no pichart
        val entry = ArrayList<PieEntry>()
        // Insere os valores nos dados do piechart
        setData(entry, totalOutcomes)

        // Cria o dataset de acordo com as entidades.
        val dataSet = PieDataSet(entry, "Outcomes Results")

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

        mPie.data = pieData
    }

    private fun setupDescription() : Description {
        val desc = Description()
        desc.text = "PieChart das Publicações"
        return desc
    }

    private fun configureOutcomesChart(totalOutcomes: TotalOutcomes) {
        val pieChart = findViewById<PieChart>(R.id.pie) ?: run {
            println("ERROR: ID do Piechart não encontrado")
            return
        }

        pieChart.description = setupDescription()
        configureLegend(pieChart.legend)
        pieChart.centerText = generateCenterSpannableText(totalOutcomes.total)
        configurePieChartSlices(pieChart, totalOutcomes)
    }

    private fun readAndGetOutcomesJSONFile() : TotalOutcomes {
        val gson = Gson()
        val jFile = assets.open("total_outcomes.json")
        val bufferedReader: BufferedReader = jFile.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        return gson.fromJson(inputString, TotalOutcomes::class.java)
    }

    private fun setData(entry: ArrayList<PieEntry>, totalOutcomes: TotalOutcomes) {
        entry.add( PieEntry( totalOutcomes.awards.toFloat(), "Awards"))
        entry.add( PieEntry( totalOutcomes.intellectualProperties.toFloat(), "Intellectual Properties"))
        entry.add( PieEntry( totalOutcomes.publications.toFloat(), "Publications"))
        entry.add( PieEntry( totalOutcomes.theses.toFloat(), "Theses"))
    }

    private fun generateCenterSpannableText(total: Int): CharSequence {
        val msg01 = SpannableString("We've all got ")
        msg01.setSpan(RelativeSizeSpan(1.4f), 0, msg01.length, 0)

        val msg02 = SpannableString("Outcomes")
        msg02.setSpan(StyleSpan(Typeface.NORMAL), 0, msg02.length, 0)
        msg02.setSpan(ForegroundColorSpan(Color.RED), 0, msg02.length, 0)
        msg02.setSpan(RelativeSizeSpan(2.1f), 0, msg02.length, 0)

        val msg03 = SpannableString(".\nWe have ")
        msg03.setSpan(RelativeSizeSpan(1.4f), 0, msg03.length, 0)

        val msg04 = SpannableString("${total}")
        msg04.setSpan(StyleSpan(Typeface.NORMAL), 0, msg04.length, 0)
        msg04.setSpan(ForegroundColorSpan(Color.GREEN), 0, msg04.length, 0)
        msg04.setSpan(RelativeSizeSpan(1.8f), 0, msg04.length, 0)

        val msg05 = SpannableString(" in total.")
        msg05.setSpan(RelativeSizeSpan(1.4f), 0, msg05.length, 0)

        return TextUtils.concat(msg01, msg02, msg03, msg04, msg05)
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