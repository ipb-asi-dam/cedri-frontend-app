package com.example.cedri_app.model.response

import com.google.gson.annotations.SerializedName

data class AnnualItem (
    @SerializedName("year")
    val year: Int,
    @SerializedName("qty")
    val qty: Int
)