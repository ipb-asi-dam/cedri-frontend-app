package com.example.cedri_app.model

import android.content.Context
import retrofit2.Callback
import android.widget.Toast
import retrofit2.Call
import retrofit2.Response

internal abstract class NetworkResponse<ResponseType>(val baseContext : Context) : Callback<ResponseType> {

    override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(call: Call<ResponseType>, t: Throwable) {
        Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
    }
}