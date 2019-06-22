package com.example.cedri_app.model

class WorkCard(
    val type : WorkType,
    val contents : String,
    val act : Class<*>
) {

    enum class WorkType {
        COMMUNICATION_NEWS {
            override fun getDisplayName() : String = "NEWS"
        },

        COMMUNICATION_EVENT {
            override fun getDisplayName() : String = "EVENT"
        },

        COMMUNICATION_MEDIA {
            override fun getDisplayName() : String = "MEDIA"
        },

        OUTCOMES_PUBLICATIONS {
            override fun getDisplayName() : String = "PUBLICATIONS"
        },

        OUTCOMES_INTELLECTUAL_PROPERTIES {
            override fun getDisplayName() : String = "INTELLECTUAL PROPERTIES"
        },

        OUTCOMES_THESES {
            override fun getDisplayName() : String = "THESES"
        },

        OUTCOMES_AWARDS {
            override fun getDisplayName() : String = "AWARDS"
        },

        RESEARCH_AND_INNOVATION_PROJECTS {
            override fun getDisplayName() : String = "PROJECTS"
        };

        abstract fun getDisplayName() : String
    }
}