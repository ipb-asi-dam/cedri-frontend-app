package com.example.cedri_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestRetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_retrofit)
        createData()
        //getData()
    }

    fun getData() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("https://jsonplaceholder.typicode.com")

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getPosts()

        callback.enqueue(object: Callback<List<Posts>> {
            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                response.body()?.forEach {
                    textView.text = textView.text.toString().plus(it.body)
                }

            }
        })
    }

    fun createData() {
        val post = Posts(19, 0, "TESTE KOTLIN", "BODY TEST KOTLIN 123456");
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("https://jsonplaceholder.typicode.com")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        val callback = endpoint.postPosts(post)

        callback.enqueue(object: Callback<Posts> {
            override fun onFailure(call: Call<Posts>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                response.body()?.let {
                    textView.text = textView.text.toString().plus(it.id)
                    // textView.text = textView.text.toString().plus(it.body)
                }
            }
        })
    }

}