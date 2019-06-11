package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class TotalOutcomes (
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("publications")
    val publications: TotalPublications,
    @SerializedName("theses")
    val theses: TotalTheses,
    @SerializedName("intellectual_properties")
    val intellectualProperties: TotalIntellectualProperties,
    @SerializedName("awards")
    val awards: Int
)
