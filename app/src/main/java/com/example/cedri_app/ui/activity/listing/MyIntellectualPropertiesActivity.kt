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
import com.example.cedri_app.model.tables.IntellectualPropertyModel
import com.example.cedri_app.ui.adapter.MyIntellectualPropertiesAdapter
import kotlinx.android.synthetic.main.activity_my_intellectual_properties.*
import kotlinx.android.synthetic.main.activity_my_intellectual_properties.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyIntellectualPropertiesActivity : AppCompatActivity() {
    private val myIntellectualProperties: MutableList<IntellectualPropertyModel> = mutableListOf()
    private val SOFTWARE = "SOFTWARE"
    private val PATENT = "PATENT"
    var currentPageOfSoftwareList = 1
    var currentPageOfPatentList = 1
    var lastPageOfSoftwareList = -1
    var lastPageOfPatentList = -1
    var isLoading = false
    val LIMIT = 15
    var token = ""
    lateinit var adapter: MyIntellectualPropertiesAdapter
    lateinit var layoutManager: LinearLayoutManager
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_intellectual_properties)
        token = NetworkUtils.getTokenFromDB(this)
        NetworkUtils.setupAvatar(this, token, profile_image_button)
        this.menu_bar.text = this.resources.getString(
            R.string.menu_bar_title_specific_work, "INTELLECTUAL PROPERTIES")
        back_image_button.setOnClickListener {
            val intent = Intent(this, WorkCardListActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        val act = this
        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        getPage(token, this, SOFTWARE)
        getPage(token, this, PATENT)

        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                tryGetPatentPage(act)
                tryGetSoftwarePage(act)
                super.onScrolled(recyclerView, dx, dy)
            }
        }

        recycler_view.addOnScrollListener(scrollListener)
    }
    private fun tryGetSoftwarePage(act : Context) {
        if ( !stillHavePagesToDisplay(currentPageOfSoftwareList, lastPageOfSoftwareList) &&
             !stillHavePagesToDisplay(currentPageOfPatentList, lastPageOfPatentList)) {
            recycler_view.removeOnScrollListener(scrollListener)
            return Toast.makeText(act, "All Intellectual Properties have been shown", Toast.LENGTH_LONG).show()
        }
        val visibleItemCount = layoutManager.childCount
        val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        val total = adapter.itemCount

        if ( isLoading ||
            !isCurrentEndOfList(visibleItemCount, pastVisibleItem, total) ||
            !stillHavePagesToDisplay(currentPageOfSoftwareList, lastPageOfSoftwareList)
        ) {
            return
        }
        currentPageOfSoftwareList++
        getPage(token, act, SOFTWARE)
    }

    private fun tryGetPatentPage(act : Context) {
        if ( !stillHavePagesToDisplay(currentPageOfSoftwareList, lastPageOfSoftwareList) &&
             !stillHavePagesToDisplay(currentPageOfPatentList, lastPageOfPatentList)) {
            recycler_view.removeOnScrollListener(scrollListener)
            return Toast.makeText(act, "All Intellectual Properties have been shown", Toast.LENGTH_LONG).show()
        }

        val visibleItemCount = layoutManager.childCount
        val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        val total = adapter.itemCount

        if ( isLoading ||
            !isCurrentEndOfList(visibleItemCount, pastVisibleItem, total) ||
            !stillHavePagesToDisplay(currentPageOfPatentList, lastPageOfPatentList)) {
            return
        }
        currentPageOfPatentList++

        getPage(token, act, PATENT)
    }

    /* As you roll, you're at the bottom of the list? */
    private fun isCurrentEndOfList(
        visibleItemCount : Int, pastVisibleItem : Int, total : Int) : Boolean {
        return (visibleItemCount + pastVisibleItem) >= total
    }

    private fun stillHavePagesToDisplay(currentPage : Int, lastPage : Int) : Boolean {
        return lastPage > currentPage
    }

    private fun initializeAdapter(): MyIntellectualPropertiesAdapter {
        return MyIntellectualPropertiesAdapter(myIntellectualProperties, this) { software, position ->
            Toast.makeText(this, "Intellectual Property selected", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPage(token : String, act : Context, type : String) {
        isLoading = true
        progress_bar.visibility = View.VISIBLE
        tryGetData(token, act, type)
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

    private fun tryGetData(token : String, act : Context, type : String) {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)

        val callback =
            if(type == SOFTWARE) endpoint.listMySoftware(true, currentPageOfSoftwareList, LIMIT)
            else endpoint.listMyPatents(true, currentPageOfPatentList, LIMIT)
        requestData(callback, act, type)
    }

    private fun success(
        act: Context,
        response: Response<AuthenticateResponse<ElementList<IntellectualPropertyModel>>>,
        type: String
    ) {
        val responseChecker = ResponseChecker(act, response)
        if ( !responseChecker.checkResponse() ) {
            return
        }

        val elementsInfo = response.body()?.getData() ?: run {
            return Toast.makeText(baseContext, "Data not found", Toast.LENGTH_SHORT).show()
        }

        val intellectualProperties = elementsInfo.elements
        if (elementsInfo.elements.isEmpty()) {
            return Toast.makeText(baseContext, "Intellectual Property (${type}) not found", Toast.LENGTH_SHORT).show()
        }

        if ((type == PATENT && elementsInfo.pagesTotal < currentPageOfPatentList) ||
            (type == SOFTWARE && elementsInfo.pagesTotal < currentPageOfSoftwareList) ) {
            return Toast.makeText(baseContext, "All your Intellectual Property ($type) have been listed", Toast.LENGTH_SHORT)
                .show()
        }

        if (lastPageOfSoftwareList == -1 && type == SOFTWARE ) {
            lastPageOfSoftwareList = elementsInfo.pagesTotal
        }
        if (lastPageOfPatentList == -1 && type == PATENT ) {
            lastPageOfPatentList = elementsInfo.pagesTotal
        }

        intellectualProperties.forEach { myIntellectualProperties.add(it) }
    }

    private fun requestData(callback : Call<AuthenticateResponse<ElementList<IntellectualPropertyModel>>>, act : Context, type : String) {
        callback.enqueue(object : Callback<AuthenticateResponse<ElementList<IntellectualPropertyModel>>> {
            override fun onFailure(call: Call<AuthenticateResponse<ElementList<IntellectualPropertyModel>>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<ElementList<IntellectualPropertyModel>>>,
                                    response: Response<AuthenticateResponse<ElementList<IntellectualPropertyModel>>>
            ) { success(act, response, type) }
        })
    }
}