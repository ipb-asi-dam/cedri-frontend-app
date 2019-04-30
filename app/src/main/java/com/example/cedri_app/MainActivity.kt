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
                editTextEmail.error = "COLOQUE UM EMAIL"
            } else if (utilsTeste.validarEmail(editTextEmail.text.toString())){
                Toast.makeText(this,"EMAIL INVALIDO", Toast.LENGTH_LONG).show()
            } else if (editTextPassword.text.isEmpty()) {
                editTextPassword.error = "COLOQUE UMA SENHA"
            } else if (utilsTeste.validarSenha("a2")){
                Toast.makeText(this,"SENHA INVALIDA", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"EMAIL E SENHA INVALIDAS", Toast.LENGTH_LONG).show()
            }

        }
    }
}
