package com.example.cedri_app.model.tables

enum class TheseType {
    msc {
        override fun getDisplayName() : String = "Master of Science"
    },

    phd {
        override fun getDisplayName() : String = "Philosophiae Doctor"
    };

    abstract fun getDisplayName() : String
}