package com.example.cedri_app.model.tables

enum class ProjectType {
    i {
        override fun getDisplayName() : String = "International"
    },

    n {
        override fun getDisplayName() : String = "National"
    },

    o {
        override fun getDisplayName() : String = "Others"
    };

    abstract fun getDisplayName() : String
}