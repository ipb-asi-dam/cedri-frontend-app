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
import com.example.cedri_app.model.tables.SoftwareModel
import com.example.cedri_app.ui.adapter.PublicationListAdapter
import kotlinx.android.synthetic.main.activity_my_intellectual_property_list.*
import kotlinx.android.synthetic.main.activity_my_award_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IntellectualPropertiesListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_intellectual_property_list)
        val token = NetworkUtils.getToken(intent.extras)
        //tryGetData(token, this)

        back_image_button.setOnClickListener {
            val intent = Intent(this, WorkCardListActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }
    }
/*
    private fun tryGetData(token : String, act : Context) {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.listMySoftware()
        requestData(callback, act)
    }

    private fun requestData(callback : Call<AuthenticateResponse<List<SoftwareModel>>>, act : Context) {
        callback.enqueue(object : Callback<AuthenticateResponse<List<SoftwareModel>>> {
            override fun onFailure(call: Call<AuthenticateResponse<List<SoftwareModel>>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<List<SoftwareModel>>>,
                                    response: Response<AuthenticateResponse<List<SoftwareModel>>>
            ) {
                val responseChecker = ResponseChecker(act, response)

                if ( responseChecker.checkResponse() ) {
                    val intellectualProperties = response.body()?.getData() ?: run {
                        val errorMsg = "DADOS NÃO ENCONTRADOS"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    val recyclerView = intellectual_properties_list_recyclerview
                    recyclerView.setHasFixedSize(true)

                    recyclerView.adapter = PublicationListAdapter(intellectualProperties, act) { intellectualPropertie, position ->
                        Toast.makeText(
                            act,
                            "Clicando no item do recyclerView o(≧▽≦)o", Toast.LENGTH_LONG
                        ).show()
                    }

                    val layoutManager = LinearLayoutManager(act)
                    //val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView.layoutManager = layoutManager
                }
            }
        })
    }*/
}