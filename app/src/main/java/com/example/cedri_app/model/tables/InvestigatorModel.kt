package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName
import java.util.*

data class InvestigatorModel (
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("bio")
    val bio : String,
    @SerializedName("isAdmin")
    val isAdmin : Boolean,
    @SerializedName("occupation")
    val occupation : Int
)