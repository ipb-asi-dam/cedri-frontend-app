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
import kotlinx.android.synthetic.main.activity_menu.logoutImageButton2
import kotlinx.android.synthetic.main.toolbar_layout.*

class MenuActivity : AppCompatActivity() {

    var myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        // val token = NetworkUtils.getToken( getIntent().getExtras() )

        val CursorDatabase = myDB.getTokenFromDatabase()
        var token : String = ""

        if(CursorDatabase.moveToFirst()){
            token = CursorDatabase.getString(CursorDatabase.getColumnIndex("token"))
        }

        NetworkUtils.setupAvatar(this, token, logoutImageButton2)
        Log.e("DB", "DADOS-> ${CursorDatabase}")

        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        itemMenuColum1Row1.setOnClickListener {
            val intent = Intent(this, ChartListActivity::class.java)
            //intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        itemMenuColum1Row2.setOnClickListener {
            val intent = Intent(this, ApprovalsActivity::class.java)
            //intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        itemMenuColum2Row1.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            //intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        itemMenuColum2Row2.setOnClickListener {
            Toast.makeText(this, "NÃO PLANEJADO", Toast.LENGTH_LONG).show()
            val intent = Intent(this, AuthorPerfilActivity::class.java)
            //intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        itemMenuColum1Row3.setOnClickListener {
            val intent = Intent(this, WorkCardListActivity::class.java)
            //intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }
    }
}
