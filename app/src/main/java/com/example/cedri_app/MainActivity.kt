package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            if (editTextEmail.text.isEmpty()){
                editTextEmail.error = "PREENCHA ESTE CAMPO"
            } else if (!(utilsTeste.validarEmail(editTextEmail.text.toString(), this))){
                // Toast.makeText(this,"EMAIL INVALIDO", Toast.LENGTH_LONG).show()
                editTextEmail.error = "EMAIL INVALIDO"
            } else if (editTextPassword.text.isEmpty()) {
                editTextPassword.error = "PREENCHA ESTE CAMPO"
            } else if (!(utilsTeste.validarSenha(editTextPassword.text.toString()))){
                // Toast.makeText(this,"PREENCHA ESTE CAMPO", Toast.LENGTH_LONG).show()
                editTextPassword.error = "SENHA INVALIDA"
            } else {
                Toast.makeText(this,"CAMPOS CORRETOS", Toast.LENGTH_LONG).show()
            }

            /* TODO: MAKE COMMUNICATION WITH THE BACKEND (WHEN IT EXISTS) */

        }
    }
}
