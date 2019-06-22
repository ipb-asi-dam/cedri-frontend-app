package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class ApprovalProjectCard (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("isApproved")
    val isApproved: Boolean
)