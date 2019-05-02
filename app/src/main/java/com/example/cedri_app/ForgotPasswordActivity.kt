package com.example.cedri_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        buttonRecover.setOnClickListener {
            var utilsTeste = Utils()
            if (editTextEmailForgotPasswordActivity.text.isEmpty()){
                editTextEmailForgotPasswordActivity.error = "PREENCHA ESTE CAMPO"
            } else if (!(utilsTeste.validarEmail(editTextEmailForgotPasswordActivity.text.toString()))){
                // Toast.makeText(this,"EMAIL INVALIDO", Toast.LENGTH_LONG).show()
                editTextEmailForgotPasswordActivity.error = "EMAIL INVALIDO"
            } else if (editTextNewPasswordForgotPasswordActivity.text.isEmpty()) {
                editTextNewPasswordForgotPasswordActivity.error = "PREENCHA ESTE CAMPO"
            } else if (!(utilsTeste.validarSenha(editTextNewPasswordForgotPasswordActivity.text.toString()))){
                // Toast.makeText(this,"PREENCHA ESTE CAMPO", Toast.LENGTH_LONG).show()
                editTextNewPasswordForgotPasswordActivity.error = "SENHA INVALIDA"
            } else if (editTextNewPasswordRepeatForgotPasswordActivity.text.isEmpty()) {
                editTextNewPasswordRepeatForgotPasswordActivity.error = "PREENCHA ESTE CAMPO"
            } else if (!(utilsTeste.validar2Senhas(editTextNewPasswordForgotPasswordActivity.text.toString(), editTextNewPasswordRepeatForgotPasswordActivity.text.toString()))) {
                editTextNewPasswordRepeatForgotPasswordActivity.error = "SENHA DIFERENTE"
            } else {
                Toast.makeText(this,"CAMPOS CORRETOS", Toast.LENGTH_LONG).show()
            }
            /* TODO: Fazer com que a tela de login seja retornada caso tenha sucesso no Recorvery */
        }

        buttonCancel.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}
