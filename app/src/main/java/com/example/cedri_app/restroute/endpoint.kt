package com.example.cedri_app

import com.example.cedri_app.model.*
import com.example.cedri_app.model.response.AnnualItem
import com.example.cedri_app.model.response.ElementList
import com.example.cedri_app.model.tables.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Endpoint {
    @POST("api/public/authenticate")
    fun postLogin(@Body authRequest: AuthenticateRequest): Call<AuthenticateResponse<Token>>

    @GET("api/public/files/{md5}")
    fun getFile(@Path("md5") md5 : String): Call<ResponseBody>

    @GET("api/private/users/{investigator_id}")
    fun showInvestigator(@Path("investigator_id") investigator_id : Int): Call<AuthenticateResponse<Investigator>>

    /* awards endpoints start */
    @GET("api/private/statistics/awards")
    fun indexTotalAwards(): Call<AuthenticateResponse<TotalAwards>>

    @GET("api/private/statistics/awards/{investigator_id")
    fun showTotalAwards(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalAwards>>

    @GET("api/private/statistics/awards")
    fun list5YearsAwardData(@Query("startYear") startYear: Int,
                            @Query("endYear") endYear: Int
    ): Call<AuthenticateResponse<List<AnnualItem>>>

    @GET("/api/private/awards")
    fun listMyAwards(
        @Query("showMy") showMy: Boolean,
        @Query("page") pageNumber: Int,
        @Query("limit") limit: Int
    ) : Call<AuthenticateResponse<ElementList<AwardModel>>>
    /* awards endpoints end */

    /* intellectual properties endpoints start */
    @GET("api/private/statistics/intellectual_properties")
    fun indexTotalIntellectualProperties(): Call<AuthenticateResponse<TotalIntellectualProperties>>

    @GET("api/private/statistics/intellectual_properties/{investigator_id")
    fun showTotalIntellectualProperties(
        @Path("investigator_id") investigator_id: Int
    ): Call<AuthenticateResponse<TotalIntellectualProperties>>
    /* intellectual properties endpoints end */

    /* publications endpoints start */
    @GET("api/private/statistics/publications")
    fun indexTotalPublications(): Call<AuthenticateResponse<TotalPublications>>

    @GET("api/private/statistics/publications/{investigator_id}")
    fun indexTotalPublications(
        @Path("investigator_id") investigator_id: Int
    ): Call<AuthenticateResponse<TotalPublications>>

    @GET("api/private/statistics/publications")
    fun list5YearsPublicationData(@Query("startYear") startYear: Int,
                            @Query("endYear") endYear: Int
    ): Call<AuthenticateResponse<List<AnnualItem>>>

    @GET("/api/private/publications")
    fun listMyPublications(
        @Query("showMy") showMy: Boolean,
        @Query("page") pageNumber: Int,
        @Query("limit") limit: Int
    ) : Call<AuthenticateResponse<ElementList<PublicationModel>>>
    /* publications endpoints end */

    /* outcomes endpoints start */
    @GET("api/private/statistics/outcomes/")
    fun indexTotalOutcomes(): Call<AuthenticateResponse<TotalOutcomes>>

    @GET("api/private/statistics/outcomes/{investigator_id")
    fun showTotalOutcomes(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalOutcomes>>
    /* outcomes endpoints end */

    /* theses endpoints start */
    @GET("api/private/statistics/theses/")
    fun indexTotalTheses(): Call<AuthenticateResponse<TotalTheses>>

    @GET("api/private/statistics/theses/{investigator_id")
    fun showTotalTheses(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalTheses>>

    @GET("api/private/statistics/theses")
    fun list5YearsTheseData(@Query("startYear") startYear: Int,
                                  @Query("endYear") endYear: Int
    ): Call<AuthenticateResponse<List<AnnualItem>>>

    @GET("api/private/theses")
    fun listMyTheses(
        @Query("showMy") showMy: Boolean,
        @Query("page") pageNumber: Int,
        @Query("limit") limit: Int
    ): Call<AuthenticateResponse<ElementList<TheseModel>>>
    /* theses endpoints end */

    /* project endpoints start */
    @GET("api/private/projects/")
    fun indexProject(@Query("page") page_number: Int, @Query("limit") limit: Int): Call<AuthenticateResponse<ApprovalProjectList>>

    @GET("api/private/projects")
    fun listMyProjects(
        @Query("showMy") showMy: Boolean,
        @Query("page") pageNumber: Int,
        @Query("limit") limit: Int
    ): Call<AuthenticateResponse<ElementList<ProjectModel>>>

    @GET("api/private/statistics/projects/")
    fun indexTotalProjects(): Call<AuthenticateResponse<TotalProjects>>

    @GET("api/private/statistics/projects/{investigator_id}")
    fun showTotalProjects(@Path("investigator_id") investigator_id: Int): Call<AuthenticateResponse<TotalProjects>>

    @GET("api/private/statistics/projects")
    fun list5YearsProjectData(@Query("startYear") startYear: Int,
                              @Query("endYear") endYear: Int
    ): Call<AuthenticateResponse<List<AnnualItem>>>
    /* project endpoints end */

    /* sofware endpoints start */
    @GET("/api/private/software")
    fun listMySoftware(
        @Query("showMy") showMy: Boolean,
        @Query("page") pageNumber: Int,
        @Query("limit") limit: Int
    ) : Call<AuthenticateResponse<ElementList<IntellectualPropertyModel>>>
    /* sofware endpoints end */

    /* patents endpoints start */
    @GET("/api/private/patents")
    fun listMyPatents(
        @Query("showMy") showMy: Boolean,
        @Query("page") pageNumber: Int,
        @Query("limit") limit: Int
    ) : Call<AuthenticateResponse<ElementList<IntellectualPropertyModel>>>
    /* patents endpoints end */
}