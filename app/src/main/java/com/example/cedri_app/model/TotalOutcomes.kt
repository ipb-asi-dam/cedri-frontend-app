package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class TotalOutcomes (
    @SerializedName("publications")
    val publications: Int,
    @SerializedName("theses")
    val theses: Int,
    @SerializedName("intellectual_properties")
    val intellectualProperties: Int,
    @SerializedName("awards")
    val awards: Int,
    @SerializedName("total")
    val total: Int
)
