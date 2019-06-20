package com.example.cedri_app.model

import com.example.cedri_app.Endpoint
import java.io.Serializable

data class Chart<T: Total, E: Endpoint<T>>(
    val title: String,
    val description: String,
    val totalClass: Class<T>,
    val cls: Class<E>
) : Serializable