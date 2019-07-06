package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cedri_app.database.DatabaseHandler
import com.example.cedri_app.ui.activity.listing.ChartListActivity
import com.example.cedri_app.ui.activity.listing.WorkCardListActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        itemMenuColum1Row1.setOnClickListener {
            val intent = Intent(this, ChartListActivity::class.java)
            startActivity(intent)
            finish()
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

        itemMenuColum1Row3.setOnClickListener {
            val intent = Intent(this, WorkCardListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
