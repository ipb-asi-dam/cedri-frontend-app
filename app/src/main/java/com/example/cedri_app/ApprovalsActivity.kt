package com.example.cedri_app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.cedri_app.database.DatabaseHandler
import com.example.cedri_app.model.ApprovalProjectCard
import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.ResponseChecker
import com.example.cedri_app.model.Total
import com.example.cedri_app.model.tables.ApprovalProjectList
import com.example.cedri_app.ui.adapter.ApporvalsAdapter
import com.example.cedri_app.ui.adapter.ChartListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_approvals.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader

class ApprovalsActivity : AppCompatActivity() {

    // var myDB = DatabaseHandler(this)

    val articlesApprovalsList: MutableList<ApprovalProjectCard> = mutableListOf()

    var page = 1
    var isLoading = false
    val limit = 15

    lateinit var adapter: ApporvalsAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approvals)

        val token = NetworkUtils.getTokenFromDB(this)

        backImageButtonArticlesReview.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        layoutManager = LinearLayoutManager(this)
        approval_recyclerView.layoutManager = layoutManager
        getPage(token)

        approval_recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount

                if (!isLoading) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        page++
                        getPage(token)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    fun getPage(token: String) {
        isLoading = true
        approval_progressBar.visibility = View.VISIBLE
        val start = articlesApprovalsList.size
        val end = articlesApprovalsList.size + limit

        retriveBackendData(token)

        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                adapter = ApporvalsAdapter(this) { articleApproved, position ->
                    Toast.makeText(
                        this,
                        "Artigo ${position} selecionado", Toast.LENGTH_LONG
                    ).show()

                    Log.e("ARTIGOAPPROVED: ", "${articleApproved}")

                    val intent = Intent(this, ArticleReviewActivity::class.java)
                    intent.putExtra("idInvestigator", articleApproved.id)
                    startActivity(intent)
                    finish()
                }
                approval_recyclerView.adapter = adapter
            }
            isLoading = false
            approval_progressBar.visibility = View.GONE
        }, 2000)
    }

    fun retriveBackendData(token : String){
        val retrofitClient = NetworkUtils.getRetrofit(token)

        // instanciando um cliente Retrofit
        val service = retrofitClient.create(Endpoint::class.java)
        val call = service.indexProject(page,limit)

        //callback (async)
        call.enqueue(object : Callback<AuthenticateResponse<ApprovalProjectList>>{
            override fun onFailure(call: Call<AuthenticateResponse<ApprovalProjectList>>, t: Throwable) {
                Toast.makeText(applicationContext,"CAMPOS INCORRETOS", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<ApprovalProjectList>>, response: Response<AuthenticateResponse<ApprovalProjectList>>) {
                val body = response.body()
                val projects = body?.getData()?.elements
                val size = projects?.size

                if (projects != null){
                    // executa se o limite de paginas n ultrapassar o enviado pelo backend
                    if (body.getData().pagesTotal >= page){
                        for (i in 0 until projects.size){
                            articlesApprovalsList.add(projects[i])
                        }
                    } else {
                        return
                    }
                }
            }
        })
    }

}
