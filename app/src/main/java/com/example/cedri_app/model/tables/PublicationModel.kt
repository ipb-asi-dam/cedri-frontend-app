package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName
import java.util.*

data class PublicationModel(
    @SerializedName("id")
    val id : Int,
    @SerializedName("authors")
    val authors : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("year")
    val year : Date,
    @SerializedName("sourceTitle")
    val sourceTitle : String,
    @SerializedName("volume")
    val volume : String,
    @SerializedName("issue")
    val issue : String,
    @SerializedName("artNumber")
    val artNumber : String,
    @SerializedName("startPage")
    val startPage : String,
    @SerializedName("endPage")
    val endPage : String,
    @SerializedName("url")
    val url : String,
    @SerializedName("doi")
    val doi : String,
    @SerializedName("indexed")
    val indexed : String,
    @SerializedName("type")
    val type : PublicationType,
    @SerializedName("investigatorId")
    val investigator : InvestigatorModel
)