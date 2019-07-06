package com.example.cedri_app.model

class ChartList(val isAdmin : Boolean) {

    fun getCharts() : List<Chart> {
        when {
            isAdmin == true -> return listOf (
                Chart("CeDRI Awards"),
                Chart("CeDRI Intellectual Properties"),
                Chart("CeDRI Outcomes"),
                Chart("CeDRI Publications"),
                Chart("CeDRI Theses"),
                Chart("My Awards (last 5 years)"),
                Chart("My Publications (last 5 years)"),
                Chart("My Theses (last 5 years)"),
                Chart("My Projects (last 5 years)")
            )
            else -> return listOf (
                Chart("My Awards (last 5 years)"),
                Chart("My Publications (last 5 years)"),
                Chart("My Theses (last 5 years)"),
                Chart("My Projects (last 5 years)")
            )
        }
    }
}