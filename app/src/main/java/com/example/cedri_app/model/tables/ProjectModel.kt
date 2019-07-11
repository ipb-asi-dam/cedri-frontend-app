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
    @SerializedName("startDate")
    val startDate : Date,
    @SerializedName("endDate")
    val endDate : Date,
    @SerializedName("webPage")
    val webPage : String,
    @SerializedName("isAccepted")
    val isAccepted : Boolean,
    @SerializedName("type")
    val type : String,
    @SerializedName("investigator")
    val investigator : InvestigatorModel
)