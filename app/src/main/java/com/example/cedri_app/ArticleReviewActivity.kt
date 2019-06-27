package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cedri_app.database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_article_review.*

class ArticleReviewActivity : AppCompatActivity() {

    var myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_review)

        val CursorDatabase = myDB.getTokenFromDatabase()
        var token : String = ""

        if(CursorDatabase.moveToFirst()){
            token = CursorDatabase.getString(CursorDatabase.getColumnIndex("token"))
        }

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

    }
}
