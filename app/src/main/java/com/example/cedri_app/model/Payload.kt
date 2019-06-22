package com.example.cedri_app.model

data class Payload (
    val id : Int,
    val isAdmin : Boolean,
    val iat : Int,
    val exp : Int
)