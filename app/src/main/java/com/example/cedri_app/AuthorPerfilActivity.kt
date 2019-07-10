package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cedri_app.model.*
import kotlinx.android.synthetic.main.activity_author_perfil.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.BitmapFactory
import android.graphics.Bitmap

class AuthorPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_perfil)

        val token = NetworkUtils.getTokenFromDB(this)
        tryGetData(this, token)

        backImageButtonAutorPerfil.setOnClickListener {
            val intent = Intent(this, ArticleReviewActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }
    }

    private fun tryGetData(mainAct : AuthorPerfilActivity, token : String) {
        val payload = Decode.decoded(token)

        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.showInvestigator(payload.id)
        callback.enqueue(object : Callback<AuthenticateResponse<Investigator>> {
            override fun onFailure(call: Call<AuthenticateResponse<Investigator>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<Investigator>>, response: Response<AuthenticateResponse<Investigator>>) {
                val responseChecker = ResponseChecker(mainAct, response)

                if ( responseChecker.checkResponse() ) {
                    val investigator : Investigator = response.body()?.getData() ?: run {
                        val errorMsg = "INVESTIGADOR NÃO ENCONTRADO"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (investigator.file != null && investigator.file.mimetype.contains("image/")) {
                        tryGetImage(token, investigator.file.md5, mainAct, investigator)
                    } else {
                        configureInvestigator(investigator, null)
                    }
                }
            }
        })
    }

    private fun tryGetImage(token : String, md5 : String, mainAct : AuthorPerfilActivity, investigator: Investigator) {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getFile(md5)
        callback.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val imageInByte : ResponseBody = response.body() ?: run {
                    val errorMsg = "INVESTIGADOR NÃO ENCONTRADO"
                    Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                    return
                }
                val imageInBitmap = BitmapFactory.decodeStream(imageInByte.byteStream())
                configureInvestigator(investigator, imageInBitmap)
            }
        })
    }

    private fun configureInvestigator(investigator : Investigator, image : Bitmap?) {
        author_perfil_first_email.text = investigator.email
        author_perfil_full_name.text = investigator.name
        image?.let {
            author_perfil_avatar.setImageBitmap(it)
            logoutImageButton2.setImageBitmap(it)
        }
    }
}
