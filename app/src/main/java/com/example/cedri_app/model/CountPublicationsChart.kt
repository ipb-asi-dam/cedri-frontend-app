package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class CountPublicationsChart (
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("publications")
    var publications: CountPublications
)
