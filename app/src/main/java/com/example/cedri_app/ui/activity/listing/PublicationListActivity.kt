package com.example.cedri_app.ui.activity.listing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.cedri_app.Endpoint
import com.example.cedri_app.NetworkUtils
import com.example.cedri_app.R
import com.example.cedri_app.model.*
import com.example.cedri_app.model.response.ElementList
import com.example.cedri_app.model.tables.PublicationModel
import com.example.cedri_app.ui.adapter.PublicationListAdapter
import kotlinx.android.synthetic.main.activity_publication_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublicationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication_list)
        val token = NetworkUtils.getToken(intent.extras)
        tryGetData(token, this)

        backImageButtonPublicationList.setOnClickListener {
            val intent = Intent(this, WorkCardListActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }
    }

    private fun tryGetData(token : String, act : Context) {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.listMyPublications(true, 1, 20)
        requestData(callback, act)
    }

    private fun requestData(callback : Call<AuthenticateResponse<ElementList<PublicationModel>>>, act : Context) {
        callback.enqueue(object : Callback<AuthenticateResponse<ElementList<PublicationModel>>> {
            override fun onFailure(call: Call<AuthenticateResponse<ElementList<PublicationModel>>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<ElementList<PublicationModel>>>,
                                    response: Response<AuthenticateResponse<ElementList<PublicationModel>>>
            ) {
                val responseChecker = ResponseChecker(act, response)

                if ( responseChecker.checkResponse() ) {
                    val publications = response.body()?.getData()?.elements ?: run {
                        val errorMsg = "DADOS NÃO ENCONTRADOS"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    val recyclerView = publication_list_recyclerview
                    recyclerView.setHasFixedSize(true)

                    recyclerView.adapter = PublicationListAdapter(publications, act) { publication, position ->
                        Toast.makeText(
                            act,
                            "Clicando no item do recyclerView o(≧▽≦)o", Toast.LENGTH_LONG
                        ).show()
                    }

                    val layoutManager = LinearLayoutManager(act)
                    recyclerView.layoutManager = layoutManager
                }
            }
        })
    }
}