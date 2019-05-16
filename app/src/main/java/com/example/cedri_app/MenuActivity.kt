package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toolbar

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        setSupportActionBar(android.support.v7.widget.Toolbar(this))

        val logoutButton = findViewById<ImageButton>(R.id.logoutImageButton)

        logoutButton.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
