package com.example.cedri_app

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.cedri_app.model.*
import kotlinx.android.synthetic.main.activity_author_perfil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorPerfilActivity : AppCompatActivity() {

    val REQUEST_PHONE_CALL = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_perfil)

        val token = NetworkUtils.getToken(getIntent().getExtras())
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
        val tokenInterceptor = TokenInterceptor(token)

        val retrofitClient = NetworkUtils.setupRetrofit(tokenInterceptor, NetworkUtils.getBaseUrl())
        val endpoint = retrofitClient.create(Endpoint::class.java)
        /*
        val callback = endpoint.showInvestigator(payload.id)

        // Asynchronous request. For synchronous request, use callback.execute()
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
                    configureInvestigator(investigator)
                }
            }
        })*/
    }

    private fun configureInvestigator(investigator : Investigator) {
        author_perfil_first_email.text = investigator.email
        author_perfil_full_name.text = investigator.name
    }
}
