package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cedri_app.model.AuthenticateRequest
import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.ResponseChecker
import com.example.cedri_app.model.Token
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonForgotPassword.setOnClickListener {
            val intent = Intent (this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonLogin.setOnClickListener{
            var utilsTeste = Utils()
            if (editTextEmailActivityMain.text.isEmpty()){
                editTextEmailActivityMain.error = "PREENCHA ESTE CAMPO"
            } else if (!(utilsTeste.validarEmail(editTextEmailActivityMain.text.toString()))){
                // Toast.makeText(this,"EMAIL INVALIDO", Toast.LENGTH_LONG).show()
                editTextEmailActivityMain.error = "EMAIL INVALIDO"
            } else if (editTextPasswordActivityMain.text.isEmpty()) {
                editTextPasswordActivityMain.error = "PREENCHA ESTE CAMPO"
            } else if (!(utilsTeste.validarSenha(editTextPasswordActivityMain.text.toString()))){
                // Toast.makeText(this,"PREENCHA ESTE CAMPO", Toast.LENGTH_LONG).show()
                editTextPasswordActivityMain.error = "SENHA INVALIDA"
            } else {
                val email = editTextEmailActivityMain.text.toString();
                val password = editTextPasswordActivityMain.text.toString();
                Toast.makeText(this,"CAMPOS CORRETOS", Toast.LENGTH_LONG).show()

                loginRequest(this, email, password)
            }
        }
    }

    private fun loginRequest(mainAct : MainActivity, email: String, password: String) {
        val tokenInterceptor = TokenInterceptor("TOKEN_FROM_LOGIN")
        val retrofitClient = NetworkUtils.setupRetrofit(tokenInterceptor, NetworkUtils.getBaseUrl())

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val authRequest = AuthenticateRequest(email, password)

        val callback = endpoint.postLogin(authRequest)

        // Asynchronous request. For synchronous request, use callback.execute()
        callback.enqueue(object : Callback<AuthenticateResponse<Token>> {
            override fun onFailure(call: Call<AuthenticateResponse<Token>>, t: Throwable) {
                println("ERROR::::: ${t.message}")
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<Token>>, response: Response<AuthenticateResponse<Token>>) {
                val responseChecker = ResponseChecker(mainAct, response)

                if ( responseChecker.checkResponse() ) {
                    val intent = Intent(mainAct, MenuActivity::class.java)
                    val token = response?.body()?.getData()?.token
                    intent.putExtra("token", token)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }
}
