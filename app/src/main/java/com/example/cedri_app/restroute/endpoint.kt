package com.example.cedri_app

import com.example.cedri_app.model.AuthenticateRequest
import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.TotalOutcomes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoint {
    @GET("posts")
    fun getPosts(): Call<List<Posts>>

    @POST("posts")
    fun postPosts(@Body post: Posts): Call<Posts>

    @POST("api/public/authenticate")
    fun postLogin(@Body authRequest: AuthenticateRequest): Call<AuthenticateResponse>

    @GET("api/public/total-outcomes")
    fun indexTotalOutcomes(): Call<TotalOutcomes>

    @GET("api/public/total-outcomes/:id")
    fun showTatalOutcomesShow(): Call<TotalOutcomes>
}