package com.example.cedri_app.model.tables

enum class InvestigatorType {
    jm {
        override fun getDisplayName() : String = "Integrated Member"
    },
    rf {
        override fun getDisplayName() : String = "Research Fellowships"
    },
    c {
        override fun getDisplayName() : String = "Research Fellowships"
    },
    vr {
        override fun getDisplayName() : String = "Research Fellowships"
    };

    abstract fun getDisplayName() : String
}