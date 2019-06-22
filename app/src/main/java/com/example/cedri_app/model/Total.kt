package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

open class Total(
    @SerializedName("total")
    val total: Int
) {
    open fun getPairList() : List<Pair<String, Int>> {
        return listOf(Pair("Total", total))
    }
}