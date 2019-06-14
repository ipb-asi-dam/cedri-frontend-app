package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class ResData (
    @SerializedName("token")
    val token: String,
    @SerializedName("message")
    val message: String
)