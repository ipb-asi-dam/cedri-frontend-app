package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName

data class SoftwareModel (
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("investigatorId")
    val investigatorId : Int
)