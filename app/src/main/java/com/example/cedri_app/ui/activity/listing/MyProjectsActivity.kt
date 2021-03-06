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
import com.example.cedri_app.model.tables.ProjectModel
import com.example.cedri_app.ui.adapter.MyProjectsAdapter
import kotlinx.android.synthetic.main.activity_my_projects.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProjectsActivity : AppCompatActivity() {
    private val myProjects: MutableList<ProjectModel> = mutableListOf()
    var currentPage = 1
    var lastPage = -1
    var isLoading = false
    val LIMIT = 15
    var token = ""
    lateinit var adapter: MyProjectsAdapter
    lateinit var layoutManager: LinearLayoutManager
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_projects)
        token = NetworkUtils.getTokenFromDB(this)
        NetworkUtils.setupAvatar(this, token, profile_image_button)
        this.menu_bar.text = this.resources.getString(
            R.string.menu_bar_title_specific_work, "PROJECTS")

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
            return Toast.makeText(act, "All Projects have been shown", Toast.LENGTH_LONG).show()
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

    private fun initializeAdapter(): MyProjectsAdapter {
        return MyProjectsAdapter(myProjects, this) { project, position ->
            Toast.makeText(this, "Project selected", Toast.LENGTH_LONG).show()
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
        val callback = endpoint.listMyProjects(true, currentPage, LIMIT)
        requestData(callback, act)
    }

    private fun success(
        act: Context,
        response: Response<AuthenticateResponse<ElementList<ProjectModel>>>
    ) {
        val responseChecker = ResponseChecker(act, response)
        if ( !responseChecker.checkResponse() ) {
            return
        }

        val elementsInfo = response.body()?.getData() ?: run {
            return Toast.makeText(baseContext, "Data not found", Toast.LENGTH_SHORT).show()
        }

        val projects = elementsInfo.elements
        if (elementsInfo.elements.isEmpty()) {
            return Toast.makeText(baseContext, "Project not found", Toast.LENGTH_SHORT).show()
        }

        if (elementsInfo.pagesTotal < currentPage) {
            return Toast.makeText(baseContext, "All your Projects have been listed", Toast.LENGTH_SHORT)
                .show()
        }

        if (lastPage == -1) {
            lastPage = elementsInfo.pagesTotal
        }
        projects.forEach { myProjects.add(it) }
    }

    private fun requestData(callback : Call<AuthenticateResponse<ElementList<ProjectModel>>>, act : Context) {
        callback.enqueue(object : Callback<AuthenticateResponse<ElementList<ProjectModel>>> {
            override fun onFailure(call: Call<AuthenticateResponse<ElementList<ProjectModel>>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<ElementList<ProjectModel>>>,
                                    response: Response<AuthenticateResponse<ElementList<ProjectModel>>>
            ) { success(act, response) }
        })
    }
}