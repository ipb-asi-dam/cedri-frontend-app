package com.example.cedri_app

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cedri_app.database.DatabaseHandler
import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.tables.ProjectModel
import kotlinx.android.synthetic.main.activity_article_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleReviewActivity : AppCompatActivity() {

    var myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_review)

        val a = intent

        var idInvestigatorFromExtra = intent.getIntExtra("idInvestigator", 0)
        Log.e("ID_INVESTIGADOR", "${idInvestigatorFromExtra}")

        val token = NetworkUtils.getTokenFromDB(this)

        backImageButtonSingleArticleReview.setOnClickListener {
            val intent = Intent(this, ApprovalsActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonPerfilAutorTelaResumoArtigo.setOnClickListener {
            val intent = Intent(this, AuthorPerfilActivity::class.java)
            startActivity(intent)
            finish()
        }


        retriveBackendData(token, idInvestigatorFromExtra)


    }

    fun retriveBackendData(token : String, idInvestigatorFromExtra : Int){
        val retrofitClient = NetworkUtils.getRetrofit(token)

        val service = retrofitClient.create(Endpoint::class.java)
        // mudar o PROJECT ID -> COLOCAR O Q VEM DA TELA ANTERIOR
        val call = service.showOneProjectInfo(idInvestigatorFromExtra.toInt())


        call.enqueue(object : Callback<AuthenticateResponse<ProjectModel>>{
            override fun onFailure(call: Call<AuthenticateResponse<ProjectModel>>, t: Throwable) {
                Toast.makeText(applicationContext,"CAMPOS INCORRETOS", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<ProjectModel>>, response: Response<AuthenticateResponse<ProjectModel>>) {
                val body = response.body()
                val projectTitle = body?.getData()?.title
                val projectDescription = body?.getData()?.description
                val projectStartDate = body?.getData()?.startDate
                val projectEndDate = body?.getData()?.endDate
                val projectIsAccepted = body?.getData()?.isAccepted
                val projectAuthorId = body?.getData()?.investigatorId

                var initialDate = projectStartDate.toString().split(" ")
                var finalDate = projectEndDate.toString().split(" ")


                articleTitleArticleReviewActivity.text = projectTitle
                descriptionProject_ArticleReviewActivity.text = projectDescription
                dates_ArticleReviewActivity.text = "${initialDate[2]} ${initialDate[1]} ${initialDate.last()} - ${finalDate[2]} ${finalDate[1]} ${finalDate.last()}"
                if (projectIsAccepted!!){
                    articleIsApprovedArticleReviewActivity.text = "REJEITAR"
                } else {
                    articleIsApprovedArticleReviewActivity.text = "APROVAR"
                }
                // TODO: pegar o valor do ID para abrir a proxima INTENT

            }
        })
    }
}
