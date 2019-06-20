package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

abstract class Total(
    @SerializedName("total")
    val total: Int
) {
    abstract fun getPairList() : List<Pair<String, Int>>
}