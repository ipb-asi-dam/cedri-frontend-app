package com.example.cedri_app.model

import com.example.cedri_app.*
import com.example.cedri_app.ui.activity.chart.BarChartActivity
import com.example.cedri_app.ui.activity.chart.TotalPieChartActivity

class Chart(val title: String) {
    fun getChartActivity() : Class<*> {
        return when {
            title.contains("CeDRI") -> TotalPieChartActivity::class.java
            title.contains("My") -> BarChartActivity::class.java
            else -> TotalPieChartActivity::class.java
        }
    }

    fun getDescription() : String {
        return when {
            title == "CeDRI Awards" -> "Unknown"
            title.contains("CeDRI") -> "Pie Chart"
            else -> "Pie Chart"
        }
    }

    fun getTableName() : String {
        return when {
            title.contains("Awards") -> "Awards"
            title.contains("Outcomes") -> "Outcomes"
            title.contains("Publications") -> "Publications"
            title.contains("Theses") -> "Theses"
            title.contains("Intellectual Properties") -> "Intellectual Properties"
            title.contains("Projects") -> "Projects"
            else -> "Unknown"
        }
    }
}