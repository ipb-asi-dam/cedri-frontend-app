package com.example.cedri_app

import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.TotalPublications
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TotalPublicationsEndpoint : Endpoint<TotalPublications> {

    @GET("api/public/total-publications")
    override fun index(): Call<AuthenticateResponse<TotalPublications>>

    @GET("api/public/total-publications/{investigator_id}")
    override fun show(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<TotalPublications>>
}