package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class TotalAwards(
    @SerializedName("awards")
    val awards: Int
)