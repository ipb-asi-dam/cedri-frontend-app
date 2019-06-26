package com.example.cedri_app.model.tables

import com.example.cedri_app.model.ApprovalProjectCard
import com.google.gson.annotations.SerializedName

data class ApprovalProjectList(
    @SerializedName ("elements")
    val elements: List<ApprovalProjectCard>,
    @SerializedName ("pagesTotal")
    val pagesTotal: Int,
    @SerializedName ("countTotal")
    val countTotal : Int

)