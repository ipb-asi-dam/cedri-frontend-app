package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

class TotalProjects (
    @SerializedName("international")
    val internationals: Int,
    @SerializedName("national")
    val nationals: Int,
    @SerializedName("other")
    val others: Int,
    total: Int
) : Total(total){

    override fun getPairList(): List<Pair<String, Int>> {
        return listOf (
            Pair("Others", others),
            Pair("Internationals", internationals),
            Pair("Nationals", nationals))
    }
}
