package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName


data class ApprovalChangeValueRequest (
    @SerializedName("isAccepted")
    val isAccepted: Boolean
)