package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName

data class CeDRIFile (
    @SerializedName("id")
    val id : Int,
    @SerializedName("md5")
    val md5 : String,
    @SerializedName("mimetype")
    val mimetype : String
)