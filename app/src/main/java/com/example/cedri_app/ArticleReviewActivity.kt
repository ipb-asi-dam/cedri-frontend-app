package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_article_review.*

class ArticleReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_review)

        backImageButtonSingleArticleReview.setOnClickListener {
            val intent = Intent(this, ApprovalsActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonPerfilAutorTelaResumoArtigo.setOnClickListener {
            val intent = Intent(this, AutorPerfilActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
