package com.example.cedri_app.restroute

import com.example.cedri_app.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OtherEndpoint {
    @POST("api/public/authenticate")
    fun postLogin(@Body authRequest: AuthenticateRequest): Call<AuthenticateResponse<Token>>

    @GET("api/private/users/{investigator_id}")
    fun showInvestigator(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<Investigator>>

    /*
    @GET("api/public/total-outcomes")
    fun indexTotalOutcomes(): Call<AuthenticateResponse<TotalOutcomes>>

    @GET("api/public/total-outcomes/:id")
    fun showTotalOutcomes(): Call<AuthenticateResponse<TotalOutcomes>>

    @GET("api/public/total-publications")
    fun indexTotalPublications(): Call<AuthenticateResponse<TotalPublications>>

    @GET("api/public/total-publications/:id")
    fun showTotalPublications(): Call<AuthenticateResponse<TotalPublications>>
    */
}
