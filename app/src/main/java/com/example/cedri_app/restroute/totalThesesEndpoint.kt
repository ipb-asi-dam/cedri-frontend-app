package com.example.cedri_app

import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.TotalTheses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TotalThesesEndpoint : Endpoint<TotalTheses> {

    @GET("api/public/total-theses")
    override fun index(): Call<AuthenticateResponse<TotalTheses>>

    @GET("api/public/total-theses/{investigator_id}")
    override fun show(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<TotalTheses>>
}