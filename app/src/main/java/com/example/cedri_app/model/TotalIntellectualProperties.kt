package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

class TotalIntellectualProperties(
    @SerializedName("patents")
    val patents: Int,
    @SerializedName("software")
    val software: Int,
    total : Int
) : Total(total) {

    override fun getPairList(): List<Pair<String, Int>> {
        return listOf (
            Pair("Patents", patents),
            Pair("Softwares", software))
    }
}