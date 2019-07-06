package com.example.cedri_app.model.response

import com.google.gson.annotations.SerializedName

data class ElementList<T> (
    @SerializedName("elements")
    val elements : List<T>,
    @SerializedName("pagesTotal")
    val pagesTotal : Int,
    @SerializedName("countTotal")
    val countTotal : Int
)