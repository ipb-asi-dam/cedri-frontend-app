package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ProjectModel (
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("financedBy")
    val financedBy : String,
    @SerializedName("consortium")
    val consortium : String,
    @SerializedName("investigatorId")
    val investigatorId : Int,
    @SerializedName("startDate")
    val startDate : String,
    @SerializedName("endDate")
    val endDate : String,
    @SerializedName("isAccepted")
    val isAccepted : Boolean,
    @SerializedName("author")
    val investigatorName : String,
    @SerializedName("type")
    val type : String
)