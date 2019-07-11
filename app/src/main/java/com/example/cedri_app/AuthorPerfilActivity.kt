package com.example.cedri_app

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cedri_app.model.*
import kotlinx.android.synthetic.main.activity_author_perfil.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_perfil)
        val authorId = intent?.extras?.getInt("authorId") ?: 0
        val token = NetworkUtils.getTokenFromDB(this)
        tryGetData(this, token, authorId)

        var idProjectFromExtra = intent.getIntExtra("idProject", 0)

        backImageButtonAutorPerfil.setOnClickListener {
            val intent = Intent(this, ArticleReviewActivity::class.java)
            intent.putExtra("idProject", idProjectFromExtra)
            startActivity(intent)
            finish()
        }
    }

    private fun tryGetData(mainAct : AuthorPerfilActivity, token : String, authorId : Int) {
        var investigatorId = 0
        var isMyProfile = true
        if (authorId == 0) {
            val payload = Decode.decoded(token)
            investigatorId = payload.id
        } else {
            investigatorId = authorId
            isMyProfile = false
        }

        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.showInvestigator(investigatorId)
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
                        tryGetImage(token, investigator.file.md5, mainAct, investigator, isMyProfile)
                    } else {
                        configureInvestigator(investigator, null, isMyProfile, mainAct, token)
                    }
                }
            }
        })
    }

    private fun tryGetImage(token : String, md5 : String, mainAct : AuthorPerfilActivity, investigator: Investigator, isMyProfile : Boolean) {
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
                configureInvestigator(investigator, imageInBitmap, isMyProfile, mainAct, token)
            }
        })
    }

    private fun configureInvestigator(investigator : Investigator, image : Bitmap?, isMyProfile : Boolean, mainAct : Context, token : String) {
        author_perfil_first_email.text = investigator.email
        author_perfil_full_name.text = investigator.name
        image?.let {
            author_perfil_avatar.setImageBitmap(it)
            if (isMyProfile) {
                logoutImageButton2.setImageBitmap(it)
            } else {
                NetworkUtils.setupAvatar(mainAct, token, logoutImageButton2)
            }
        }
    }
}
