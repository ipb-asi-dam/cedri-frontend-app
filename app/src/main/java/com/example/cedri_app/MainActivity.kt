package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cedri_app.model.AuthenticateRequest
import com.example.cedri_app.model.AuthenticateResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_test_retrofit.*
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
                Toast.makeText(this,"CAMPOS CORRETOS", Toast.LENGTH_LONG).show()
                val email = editTextEmailActivityMain.text.toString();
                val password = editTextPasswordActivityMain.text.toString();

                println("EMAIL: ${email}, PASS: ${password}")
                loginRequest(this, email, password)
            }
        }

    }
    private fun loginRequest(mainAct : MainActivity, email: String, password: String) {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("http://192.168.0.104:5000")
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val authRequest = AuthenticateRequest(email, password)
        val callback = endpoint.postLogin(authRequest)

        // Asynchronous request. For synchronous request, use callback.execute()
        callback.enqueue(object : Callback<AuthenticateResponse> {
            override fun onFailure(call: Call<AuthenticateResponse>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse>, response: Response<AuthenticateResponse>) {
                println("DEU CERTO FOGUINHO")
                println("RESPONSE: ${response}")
                println("CALL: ${call}")
                // Verifica se tá ok

                // Se tiver, decodifica o JWT

                // Armazena numa variável

                // Se tiver errado, pede para tentar novamente.
                response.body()?.let {
                    println("RESULTADOS: ${it}")
                }

                val intent = Intent (mainAct, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}
