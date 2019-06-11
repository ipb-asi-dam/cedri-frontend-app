package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class AuthenticateResponse (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("msg")
    val msg: String)