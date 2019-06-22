package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

class TotalIntellectualProperties(
    @SerializedName("patents")
    val patents: Int,
    @SerializedName("softwares")
    val softwares: Int,
    total : Int
) : Total(total) {

    override fun getPairList(): List<Pair<String, Int>> {
        return listOf (
            Pair("Patents", patents),
            Pair("Softwares", softwares))
    }
}