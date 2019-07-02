package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName

data class IntellectualPropertyModel (
    @SerializedName("title")
    val title : String,
    @SerializedName("authors")
    val authors : String
)