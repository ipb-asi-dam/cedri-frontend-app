package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

class TotalTheses(
    @SerializedName("mscs")
    val msc: Int,
    @SerializedName("phds")
    val phd: Int,
    total: Int
) : Total(total) {

    override fun getPairList(): List<Pair<String, Int>> {
        return listOf (
            Pair("Master of Science", msc),
            Pair("Philosophiae Doctor", phd))
    }
}