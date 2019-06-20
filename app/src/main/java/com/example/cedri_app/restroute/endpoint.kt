package com.example.cedri_app

import com.example.cedri_app.model.*
import retrofit2.Call
import retrofit2.http.Path

interface Endpoint<T> {

    fun index(): Call<AuthenticateResponse<T>>

    fun show(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<T>>
}