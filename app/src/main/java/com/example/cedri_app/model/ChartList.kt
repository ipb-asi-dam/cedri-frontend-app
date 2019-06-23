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
                Chart("My Awards"),
                Chart("My Intellectual Properties"),
                Chart("My Outcomes"),
                Chart("My Publications"),
                Chart("My Theses"),
                Chart("My Projects")
            )
            else -> return listOf (
                Chart("My Awards"),
                Chart("My Intellectual Properties"),
                Chart("My Outcomes"),
                Chart("My Publications"),
                Chart("My Theses"),
                Chart("My Projects")
            )
        }
    }
}