package com.example.cedri_app.model

class ChartList(val isAdmin : Boolean) {

    fun getCharts() : List<Chart> {
        when {
            isAdmin == true -> return listOf (
                Chart("CEDRI Awards"),
                Chart("CEDRI Intellectual Properties"),
                Chart("CEDRI Outcomes"),
                Chart("CEDRI Publications"),
                Chart("CEDRI Theses")
            )
            else -> return listOf (
                Chart("My Awards"),
                Chart("My Intellectual Properties"),
                Chart("My Outcomes"),
                Chart("My Publications"),
                Chart("My Theses")
            )
        }
    }
}