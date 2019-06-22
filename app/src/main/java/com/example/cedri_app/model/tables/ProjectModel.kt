package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName

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
    val investigatorId : Int
)