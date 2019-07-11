package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cedri_app.model.ApprovalChangeValueRequest
import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.tables.ProjectModel
import kotlinx.android.synthetic.main.activity_article_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleReviewActivity : AppCompatActivity() {

    var buttonValue: Boolean = false
    var authorId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_review)

        var idProjectFromExtra = intent.getIntExtra("idProject", 0)
        // Log.e("ID_INVESTIGADOR", "${idProjectFromExtra}")

        val token = NetworkUtils.getTokenFromDB(this)
        NetworkUtils.setupAvatar(this, token, logoutImageButton2)
        retriveBackendData(token, idProjectFromExtra)

        backImageButtonSingleArticleReview.setOnClickListener {
            val intent = Intent(this, ApprovalsActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonPerfilAutorTelaResumoArtigo.setOnClickListener {
            val intent = Intent(this, AuthorPerfilActivity::class.java)
            intent.putExtra("authorId", authorId)
            intent.putExtra("idProject", idProjectFromExtra)
            startActivity(intent)
            finish()
        }

        articleIsApprovedArticleReviewActivity.setOnClickListener {
            // FAZER A REQUISIÇÃO COM O BACK E MUDAR O VALOR DO DO BOTAO
            // Log.e("BUTTON VALUE: ", "${buttonValue}")
            changeIsAcceptedValue(token, idProjectFromExtra, buttonValue)
            //articleIsApprovedArticleReviewActivity.text = "${buttonValue}"
        }
    }
    fun changeIsAcceptedValue(token: String, idProject: Int, changeIsAccepted: Boolean){
        val retrofitClient = NetworkUtils.getRetrofit(token)

        val service = retrofitClient.create(Endpoint::class.java)

        val putBody = ApprovalChangeValueRequest(!changeIsAccepted)
        // Log.e("SENT VALUE: ", "${!changeIsAccepted}")

        val callback = service.changeIsAcceptedProjecValue(idProject, putBody)

        callback.enqueue(object : Callback<AuthenticateResponse<ApprovalChangeValueRequest>>{
            override fun onFailure(call: Call<AuthenticateResponse<ApprovalChangeValueRequest>>, t: Throwable) {
                Toast.makeText(applicationContext,"ERRO COMUNICAÇÃO", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<AuthenticateResponse<ApprovalChangeValueRequest>>,
                response: Response<AuthenticateResponse<ApprovalChangeValueRequest>>
            ) {
                val body = response.body()
                val isAcceptedFromDB = body?.getData()?.isAccepted

                if (isAcceptedFromDB != null){
                    Log.e("FROM DB VALUE: ", "${isAcceptedFromDB}")
                    if (isAcceptedFromDB){
                        buttonValue = isAcceptedFromDB
                        articleIsApprovedArticleReviewActivity.text = "REJEITAR"
                    } else {
                        buttonValue = isAcceptedFromDB
                        articleIsApprovedArticleReviewActivity.text = "APROVAR"
                    }
                } else {
                    Toast.makeText(applicationContext,"ERRO DO SERVER", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun retriveBackendData(token : String, idProjectFromExtra : Int){
        val retrofitClient = NetworkUtils.getRetrofit(token)

        val service = retrofitClient.create(Endpoint::class.java)

        val call = service.showOneProjectInfo(idProjectFromExtra)

        call.enqueue(object : Callback<AuthenticateResponse<ProjectModel>>{
            override fun onFailure(call: Call<AuthenticateResponse<ProjectModel>>, t: Throwable) {
                Toast.makeText(applicationContext,"ERRO AO RECEBER DADOS", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<ProjectModel>>, response: Response<AuthenticateResponse<ProjectModel>>) {
                val body = response.body()
                val projectTitle = body?.getData()?.title
                val projectDescription = body?.getData()?.description
                val projectStartDate = body?.getData()?.startDate
                val projectEndDate = body?.getData()?.endDate
                val projectIsAccepted = body?.getData()?.isAccepted
                val projectAuthorId = body?.getData()?.investigator?.id

                var initialDate = projectStartDate.toString().split(" ")
                var finalDate = projectEndDate.toString().split(" ")

                if (projectAuthorId != null) {
                    authorId = projectAuthorId
                }

                articleTitleArticleReviewActivity.text = projectTitle
                descriptionProject_ArticleReviewActivity.text = projectDescription
                dates_ArticleReviewActivity.text = "${initialDate[2]} ${initialDate[1]} ${initialDate.last()} - ${finalDate[2]} ${finalDate[1]} ${finalDate.last()}"

                if (projectIsAccepted!!){
                    buttonValue = projectIsAccepted
                    // Log.e("STORED VALUE: ", "${buttonValue}")
                    articleIsApprovedArticleReviewActivity.text = "REJEITAR"
                } else {
                    buttonValue = projectIsAccepted
                    // Log.e("STORED VALUE: ", "${buttonValue}")
                    articleIsApprovedArticleReviewActivity.text = "APROVAR"
                }
            }
        })
    }
}
