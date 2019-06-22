package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

class TotalOutcomes (
    @SerializedName("publications")
    val publications: Int,
    @SerializedName("theses")
    val theses: Int,
    @SerializedName("intellectual_properties")
    val intellectualProperties: Int,
    @SerializedName("awards")
    val awards: Int,
    total: Int
) : Total(total){

    override fun getPairList(): List<Pair<String, Int>> {
        return listOf (
            Pair("Awards", awards),
            Pair("Intellectual Properties", intellectualProperties),
            Pair("Publications", publications),
            Pair("Theses", theses))
    }
}
