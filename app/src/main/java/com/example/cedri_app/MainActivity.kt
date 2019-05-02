package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

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
            }

            /* TODO: MAKE COMMUNICATION WITH THE BACKEND (WHEN IT EXISTS) */

        }
    }
}
