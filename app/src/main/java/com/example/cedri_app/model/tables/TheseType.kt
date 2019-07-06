package com.example.cedri_app.model.tables

import com.google.gson.annotations.SerializedName

enum class TheseType {
    @SerializedName("msc")
    msc {
        override fun getDisplayName() : String = "Master of Science"
    },

    @SerializedName("phd")
    phd {
        override fun getDisplayName() : String = "Philosophiae Doctor"
    };

    abstract fun getDisplayName() : String
}