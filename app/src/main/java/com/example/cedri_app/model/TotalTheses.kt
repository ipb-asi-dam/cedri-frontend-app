package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class TotalTheses(
    @SerializedName("msc")
    val msc: Int,
    @SerializedName("phd")
    val phd: Int
)