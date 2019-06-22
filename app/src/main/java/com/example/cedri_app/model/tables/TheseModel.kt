package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName
import java.util.*

data class TheseModel (
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("student")
    val student : String,
    @SerializedName("grade")
    val grade : String,
    @SerializedName("institute")
    val institute : String,
    @SerializedName("completed")
    val completed : Boolean,
    @SerializedName("date")
    val date : Date,
    @SerializedName("supervisors")
    val supervisors : Boolean,
    @SerializedName("completed")
    val type : TheseType,
    @SerializedName("investigatorId")
    val investigatorId : Int
)