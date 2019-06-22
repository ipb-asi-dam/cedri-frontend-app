package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName
import java.util.*

data class AwardModel (
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("prizeWinners")
    val prizeWinners : String,
    @SerializedName("date")
    val date : Date,
    @SerializedName("address")
    val address : String,
    @SerializedName("event")
    val event : String,
    @SerializedName("investigatorId")
    val investigatorId : Int
)