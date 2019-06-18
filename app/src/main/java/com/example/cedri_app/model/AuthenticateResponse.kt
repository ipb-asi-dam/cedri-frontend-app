package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

class AuthenticateResponse<T>(
    @SerializedName("status")
    private val status : String,
    @SerializedName("data")
    private val data : T
) {
    fun getData() : T = data
    fun getStatus() : String = status
}


