package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_autor_perfil.*

class AutorPerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autor_perfil)

        backImageButtonAutorPerfil.setOnClickListener {
            val intent = Intent(this, ArticleReviewActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
