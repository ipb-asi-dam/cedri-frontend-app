package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class AuthenticateResponse (
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: ResData
)