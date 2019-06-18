package com.example.cedri_app

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        backImageButtonSearch.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        val listView = findViewById<ListView>(R.id.search_listView)

        listView.adapter = SeachListViewAdapter(this)

    }

    private class SeachListViewAdapter(context: Context) : BaseAdapter() {

        private val mContext: Context
        private val article_names = arrayListOf<String>(
            "a", "b"
        )

        init {
            mContext = context
        }

        // responsible for rendering each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

            val layoutInflater = LayoutInflater.from(mContext)
            val row_search = layoutInflater.inflate(R.layout.card_article, viewGroup, false)

            val card_article_approval = row_search.findViewById<TextView>(R.id.cardArticle_textView)
            card_article_approval.text = "${article_names.get(position)}: $position"

            val image = mContext.resources.getDrawable(R.drawable.ic_check_circle_black_24dp)

            card_article_approval.setCompoundDrawablesWithIntrinsicBounds(null, null, image, null)

            return row_search
        }

        // ignore for now
        override fun getItem(position: Int): Any {
            return "getItemReturn"
        }

        // ignore for now
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        // responsible for how many rows in the listview
        override fun getCount(): Int {
            return article_names.size
        }

    }

}
