package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // setSupportActionBar(android.support.v7.widget.Toolbar(this))

        val logoutButton = findViewById<ImageButton>(R.id.logoutImageButton)

        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        itemMenuColum1Row1.setOnClickListener {
            Toast.makeText(this, "NAO IMPLEMENTADO", Toast.LENGTH_LONG).show()
        }

        itemMenuColum1Row2.setOnClickListener {
            val intent = Intent(this, ApprovalsActivity::class.java)
            startActivity(intent)
            finish()
        }

        itemMenuColum2Row1.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            finish()
        }

        itemMenuColum2Row2.setOnClickListener {
            Toast.makeText(this, "NÃO PLANEJADO", Toast.LENGTH_LONG).show()
        }
    }
}
