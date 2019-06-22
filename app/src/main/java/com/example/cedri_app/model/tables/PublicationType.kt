package com.example.cedri_app.model.tables

enum class PublicationType {
    j {
        override fun getDisplayName() : String = "Journal"
    },

    b {
        override fun getDisplayName() : String = "Book"
    },

    bc {
        override fun getDisplayName() : String = "Book Chapter"
    },

    p {
        override fun getDisplayName() : String = "Proceeding"
    },

    e {
        override fun getDisplayName() : String = "Editorial"
    };

    abstract fun getDisplayName() : String
}