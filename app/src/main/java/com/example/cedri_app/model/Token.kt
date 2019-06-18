package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token")
    val token : String
)