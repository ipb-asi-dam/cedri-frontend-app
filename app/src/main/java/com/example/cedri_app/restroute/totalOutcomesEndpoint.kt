package com.example.cedri_app

import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.TotalOutcomes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TotalOutcomesEndpoint : Endpoint<TotalOutcomes> {

    @GET("api/public/total-outcomes")
    override fun index(): Call<AuthenticateResponse<TotalOutcomes>>

    @GET("api/public/total-outcomes/{investigator_id}")
    override fun show(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<TotalOutcomes>>
}