package com.example.cedri_app

import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.TotalIntellectualProperties
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TotalIntellectualPropertiesEndpoint : Endpoint<TotalIntellectualProperties> {

    @GET("api/public/total-intellectual-properties")
    override fun index(): Call<AuthenticateResponse<TotalIntellectualProperties>>

    @GET("api/public/total-intellectual-properties/{investigator_id}")
    override fun show(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<TotalIntellectualProperties>>
}