package com.example.cedri_app.ui.activity.listing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.cedri_app.Endpoint
import com.example.cedri_app.NetworkUtils
import com.example.cedri_app.R
import com.example.cedri_app.model.*
import com.example.cedri_app.model.response.ElementList
import com.example.cedri_app.model.tables.TheseModel
import com.example.cedri_app.ui.adapter.MyThesesAdapter
import kotlinx.android.synthetic.main.activity_chart_list.*
import kotlinx.android.synthetic.main.activity_my_awards.*
import kotlinx.android.synthetic.main.activity_my_theses.*
import kotlinx.android.synthetic.main.activity_my_theses.back_image_button
import kotlinx.android.synthetic.main.activity_my_theses.profile_image_button
import kotlinx.android.synthetic.main.activity_my_theses.progress_bar
import kotlinx.android.synthetic.main.activity_my_theses.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyThesesActivity : AppCompatActivity() {
    private val myTheses: MutableList<TheseModel> = mutableListOf()
    var currentPage = 1
    var lastPage = -1
    var isLoading = false
    val LIMIT = 15
    var token = ""
    lateinit var adapter: MyThesesAdapter
    lateinit var layoutManager: LinearLayoutManager
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_theses)
        token = NetworkUtils.getTokenFromDB(this)
        NetworkUtils.setupAvatar(this, token, profile_image_button)

        back_image_button.setOnClickListener {
            val intent = Intent(this, WorkCardListActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        val act = this
        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                tryGetPage(act)
                super.onScrolled(recyclerView, dx, dy)
            }
        }

        getPage(token, this)
        recycler_view.addOnScrollListener(scrollListener)
    }

    private fun tryGetPage(act : Context) {
        if ( !stillHavePagesToDisplay(currentPage, lastPage) ) {
            recycler_view.removeOnScrollListener(scrollListener)
            return Toast.makeText(act, "All Theses have been shown", Toast.LENGTH_LONG).show()
        }

        val visibleItemCount = layoutManager.childCount
        val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        val total = adapter.itemCount

        if ( isLoading || !isCurrentEndOfList(visibleItemCount, pastVisibleItem, total)) {
            return
        }
        currentPage++
        getPage(token, act)
    }

    /* As you roll, you're at the bottom of the list? */
    private fun isCurrentEndOfList(
        visibleItemCount : Int, pastVisibleItem : Int, total : Int) : Boolean {
        return (visibleItemCount + pastVisibleItem) >= total
    }

    private fun stillHavePagesToDisplay(currentPage : Int, lastPage : Int) : Boolean {
        return lastPage > currentPage
    }

    private fun initializeAdapter(): MyThesesAdapter {
        return MyThesesAdapter(myTheses, this) { these, position ->
            Toast.makeText(this, "These selected", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPage(token : String, act : Context) {
        isLoading = true
        progress_bar.visibility = View.VISIBLE
        tryGetData(token, act)
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                adapter = initializeAdapter()
                recycler_view.adapter = adapter
                recycler_view.setHasFixedSize(false)
            }
            isLoading = false
            progress_bar.visibility = View.GONE
        }, 2000)
    }

    private fun tryGetData(token : String, act : Context) {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.listMyTheses(true, currentPage, LIMIT)
        requestData(callback, act)
    }

    private fun success(
        act: Context,
        response: Response<AuthenticateResponse<ElementList<TheseModel>>>
    ) {
        val responseChecker = ResponseChecker(act, response)
        if ( !responseChecker.checkResponse() ) {
            return
        }

        val elementsInfo = response.body()?.getData() ?: run {
            return Toast.makeText(baseContext, "Data not found", Toast.LENGTH_SHORT).show()
        }

        val theses = elementsInfo.elements
        if (elementsInfo.elements.isEmpty()) {
            return Toast.makeText(baseContext, "Award not found", Toast.LENGTH_SHORT).show()
        }

        if (elementsInfo.pagesTotal < currentPage) {
            return Toast.makeText(baseContext, "All your awards have been listed", Toast.LENGTH_SHORT)
                .show()
        }

        if (lastPage == -1) {
            lastPage = elementsInfo.pagesTotal
        }
        theses.forEach { myTheses.add(it) }
    }

    private fun requestData(callback : Call<AuthenticateResponse<ElementList<TheseModel>>>, act : Context) {
        callback.enqueue(object : Callback<AuthenticateResponse<ElementList<TheseModel>>> {
            override fun onFailure(call: Call<AuthenticateResponse<ElementList<TheseModel>>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<ElementList<TheseModel>>>,
                                    response: Response<AuthenticateResponse<ElementList<TheseModel>>>
            ) { success(act, response) }
        })
    }
}