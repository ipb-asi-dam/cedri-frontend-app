package com.example.cedri_app.model

import com.example.cedri_app.*
import com.example.cedri_app.ui.activity.chart.TotalPieChartActivity

class Chart(val title: String) {
    fun getTotalClass() : Class<*> {
        return when (title) {
            "CEDRI Awards" -> TotalOutcomes::class.java
            "CEDRI Intellectual Properties" -> TotalIntellectualProperties::class.java
            "CEDRI Outcomes" -> TotalOutcomes::class.java
            "CEDRI Publications" -> TotalPublications::class.java
            "CEDRI Theses" -> TotalTheses::class.java
            else -> TotalOutcomes::class.java
        }
    }

    fun getChartActivity() : Class<*> {
        return when {
            title.contains("CEDRI") -> TotalPieChartActivity::class.java
            else -> TotalPieChartActivity::class.java
        }
    }

    fun getEndpointClass() : Class<*> {
        return when {
            title.contains("CEDRI") -> Endpoint::class.java
            else -> Endpoint::class.java
        }
    }

    fun getDescription() : String {
        return when {
            title == "CEDRI Awards" -> "Unknown"
            title.contains("CEDRI") -> "Pie Chart"
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
            else -> "Unknown"
        }
    }
}