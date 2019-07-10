package com.example.cedri_app

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cedri_app.database.DatabaseHandler
import com.example.cedri_app.model.ApprovalChangeValueRequest
import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.tables.ProjectModel
import kotlinx.android.synthetic.main.activity_article_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleReviewActivity : AppCompatActivity() {

    var myDB = DatabaseHandler(this)

    var buttonValue: Boolean = false

    var authorId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_review)

        var idProjectFromExtra = intent.getIntExtra("idProject", 0)
        // Log.e("ID_INVESTIGADOR", "${idProjectFromExtra}")

        val token = NetworkUtils.getTokenFromDB(this)
        retriveBackendData(token, idProjectFromExtra)

        backImageButtonSingleArticleReview.setOnClickListener {
            val intent = Intent(this, ApprovalsActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonPerfilAutorTelaResumoArtigo.setOnClickListener {
            val intent = Intent(this, AuthorPerfilActivity::class.java)
            intent.putExtra("authorId", authorId)
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
}
