package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class ProjectModel (
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("author")
    val investigatorName : String,
    @SerializedName("startDate")
    val startDate : Date,
    @SerializedName("endDate")
    val endDate : Date,
    @SerializedName("type")
    val type : String
)