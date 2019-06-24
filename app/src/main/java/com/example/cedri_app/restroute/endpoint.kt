package com.example.cedri_app

import com.example.cedri_app.model.*
import com.example.cedri_app.model.response.AnnualItem
import com.example.cedri_app.model.tables.PublicationModel
import retrofit2.Call
import retrofit2.http.*

interface Endpoint {
    @POST("api/public/authenticate")
    fun postLogin(@Body authRequest: AuthenticateRequest): Call<AuthenticateResponse<Token>>

    @GET("api/private/users/{investigator_id}")
    fun showInvestigator(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<Investigator>>


    /* Annual Statistics */
    @GET("api/public/{work}/annual")
    fun indexAnnual(@Path("work") work: String): Call<AuthenticateResponse<List<AnnualItem>>>


    /* AWARDS */
    @GET("api/public/statistics/awards")
    fun indexTotalAwards(): Call<AuthenticateResponse<TotalAwards>>

    @GET("api/public/statistics/awards/{investigator_id")
    fun showTotalAwards(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalAwards>>


    /* INTELLECTUAL PROPERTIES */
    @GET("api/public/statistics/intellectual_properties")
    fun indexTotalIntellectualProperties(): Call<AuthenticateResponse<TotalIntellectualProperties>>

    @GET("api/public/statistics/intellectual_properties/{investigator_id")
    fun showTotalIntellectualProperties(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalIntellectualProperties>>


    /* PUBLICATIONS */
    @GET("api/public/statistics/publications")
    fun indexTotalPublications(): Call<AuthenticateResponse<TotalPublications>>

    @GET("api/public/publications/{investigator_id")
    fun showTotalPublications(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalPublications>>


    /* OUTCOMES */
    @GET("api/public/statistics/outcomes/")
    fun indexTotalOutcomes(): Call<AuthenticateResponse<TotalOutcomes>>

    @GET("api/public/statistics/outcomes/{investigator_id")
    fun showTotalOutcomes(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalOutcomes>>


    /* THESES */
    @GET("api/public/statistics/theses/")
    fun indexTotalTheses(): Call<AuthenticateResponse<TotalTheses>>

    @GET("api/public/statistics/theses/{investigator_id")
    fun showTotalTheses(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalTheses>>



    /* PROJECTS */
    @GET("api/public/projects/")
    fun indexProject(@Query("page") page_number: Int, @Query("limit") limit: Int): Call<AuthenticateResponse<ArrayList<ApprovalProjectCard>>>

    @GET("api/public/statistics/projects/")
    fun indexTotalProjects(): Call<AuthenticateResponse<TotalProjects>>

    @GET("api/public/statistics/outcomes/{investigator_id")
    fun showTotalProjects(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalProjects>>


    @GET("api/private/publications")
    fun indexPublications(): Call<AuthenticateResponse<List<PublicationModel>>>
}