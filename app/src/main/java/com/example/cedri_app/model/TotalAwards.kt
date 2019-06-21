package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

class TotalAwards(
    @SerializedName("awards")
    val awards: Int,
    total : Int
) : Total(total) {
    override fun getPairList(): List<Pair<String, Int>> {
        return listOf (
            Pair("Awards", awards))
    }
}