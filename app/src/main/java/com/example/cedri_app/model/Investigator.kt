package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class Investigator(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("bio")
    val bio : String,
    @SerializedName("occupation")
    val occupation : String?,
    @SerializedName("isAdmin")
    val isAdmin : Boolean,
    @SerializedName("email")
    val email : String,
    @SerializedName("field")
    val field : String?
)