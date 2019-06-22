package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName

data class PatentModel (
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("authors")
    val authors : String,
    @SerializedName("patentNumbers")
    val patentNumbers : String,
    @SerializedName("investigatorId")
    val investigatorId : Int
)