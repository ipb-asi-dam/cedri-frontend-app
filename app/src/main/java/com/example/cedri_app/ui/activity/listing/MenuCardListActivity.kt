package com.example.cedri_app.ui.activity.listing

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.cedri_app.MenuActivity
import com.example.cedri_app.MainActivity
import com.example.cedri_app.ApprovalsActivity
import com.example.cedri_app.model.MenuCard
import com.example.cedri_app.NetworkUtils
import com.example.cedri_app.R
import com.example.cedri_app.SearchActivity
import com.example.cedri_app.ui.adapter.MenuCardListAdapter
import kotlinx.android.synthetic.main.activity_chart_list.*
import kotlinx.android.synthetic.main.activity_menu_card_list.*

class MenuCardListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_card_list)
        val token = NetworkUtils.getTokenFromDB(this)
        NetworkUtils.setupAvatar(this, token, profile_image_button)

        back_image_button.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        val recyclerView = menu_card_list_recyclerview
        recyclerView.setHasFixedSize(true)

        val menuCards = listOf (
            MenuCard("DASHBOARD", ChartListActivity::class.java, R.drawable.ic_insert_chart_black_24dp),
            MenuCard("SEARCH", SearchActivity::class.java, R.drawable.ic_search_black_24dp),
            MenuCard("APPROVALS", ApprovalsActivity::class.java, R.drawable.ic_spellcheck_black_24dp),
            MenuCard("MY WORKS", WorkCardListActivity::class.java, R.drawable.ic_insert_chart_black_24dp),
            MenuCard("SETTINGS", MainActivity::class.java, R.drawable.ic_settings_black_24dp)
        )

        recyclerView.adapter = MenuCardListAdapter(menuCards, this) { menuCard, position ->
            Toast.makeText(
                this,
                "Selecionado uma opção(≧▽≦)o", Toast.LENGTH_LONG
            ).show()

            val intent = Intent(this, menuCard.act)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        val layoutManager = LinearLayoutManager(this)
        //val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
}