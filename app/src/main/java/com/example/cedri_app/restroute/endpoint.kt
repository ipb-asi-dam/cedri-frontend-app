package com.example.cedri_app

import com.example.cedri_app.model.*
import com.example.cedri_app.model.tables.ApprovalProjectList
import com.example.cedri_app.model.tables.PublicationModel
import retrofit2.Call
import retrofit2.http.*

interface Endpoint {
    @POST("api/public/authenticate")
    fun postLogin(@Body authRequest: AuthenticateRequest): Call<AuthenticateResponse<Token>>


    @GET("api/private/users/{investigator_id}")
    fun showInvestigator(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<Investigator>>


    @GET("api/public/awards/total")
    fun indexTotalAwards(): Call<AuthenticateResponse<TotalAwards>>

    @GET("api/public/awards/total/{investigator_id")
    fun showTotalAwards(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalAwards>>


    @GET("api/public/intellectual-properties/total")
    fun indexTotalIntellectualProperties(): Call<AuthenticateResponse<TotalIntellectualProperties>>

    @GET("api/public/intellectual-properties/total/{investigator_id")
    fun showTotalIntellectualProperties(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalIntellectualProperties>>


    @GET("api/public/publications/total")
    fun indexTotalPublications(): Call<AuthenticateResponse<TotalPublications>>

    @GET("api/public/publications/total/{investigator_id")
    fun showTotalPublications(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalPublications>>


    @GET("api/public/outcomes/total")
    fun indexTotalOutcomes(): Call<AuthenticateResponse<TotalOutcomes>>

    @GET("api/public/outcomes/total/{investigator_id")
    fun showTotalOutcomes(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalOutcomes>>


    @GET("api/public/theses/total")
    fun indexTotalTheses(): Call<AuthenticateResponse<TotalTheses>>

    @GET("api/public/theses/total/{investigator_id")
    fun showTotalTheses(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalTheses>>

    @GET("api/private/projects/")
    fun indexProject(@Query("page") page_number: Int, @Query("limit") limit: Int): Call<AuthenticateResponse<ApprovalProjectList>>

    @GET("api/private/publications")
    fun indexPublications(): Call<AuthenticateResponse<List<PublicationModel>>>

}