package com.example.cedri_app

import android.content.Context
import android.widget.Toast
import java.util.regex.Pattern

class Utils {

    val oneNumberRegex = ".*[0-9].*"
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    val especialCharRegex = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
    var passwordPatternsWithEspecialChar = Pattern.compile(especialCharRegex)
    var passwordPaternWithOneNumber = Pattern.compile(oneNumberRegex, Pattern.CASE_INSENSITIVE)

    public fun validarSenha (password:String):Boolean {
        if (password.length < 8){
            return false
        } else if (!(this.passwordPaternWithOneNumber.matcher(password).matches())){
            return false
        } else if (!(this.passwordPatternsWithEspecialChar.matcher(password).matches())) {
            return false
        }

        return true
    }

    public fun validarEmail (email:String):Boolean {
        val ptr = Pattern.compile(this.emailRegex)

        return (ptr.matcher(email).matches())
    }

    public fun validar2Senhas (password1:String, password2:String):Boolean {
        if (password1.equals(password2)){
            return true
        }
        return false
    }

    private fun showToast(contexto: Context, mensagem: String) {
        Toast.makeText(
            contexto,
            mensagem,
            Toast.LENGTH_SHORT
        ).show()
    }
}