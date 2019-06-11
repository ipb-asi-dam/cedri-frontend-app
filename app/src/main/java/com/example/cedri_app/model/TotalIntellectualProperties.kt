package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class TotalIntellectualProperties(
    @SerializedName("patents")
    val patents: Int,
    @SerializedName("softwares")
    val softwares: Int
)