package com.example.cedri_app

import com.example.cedri_app.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {
    @GET("posts")
    fun getPosts(): Call<List<Posts>>

    @POST("posts")
    fun postPosts(@Body post: Posts): Call<Posts>

    @POST("api/public/authenticate")
    fun postLogin(@Body authRequest: AuthenticateRequest): Call<AuthenticateResponse<Token>>

    @GET("api/public/total-outcomes")
    fun indexTotalOutcomes(): Call<AuthenticateResponse<TotalOutcomes>>

    @GET("api/public/total-outcomes/:id")
    fun showTatalOutcomesShow(): Call<AuthenticateResponse<TotalOutcomes>>

    @GET("api/private/users/{investigator_id}")
    fun showInvestigator(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<Investigator>>
}