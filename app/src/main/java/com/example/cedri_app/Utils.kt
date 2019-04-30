package com.example.cedri_app

class Utils {

    public fun validarSenha (pass1:String):Boolean {
        return true
    }

    public fun validarEmail (email:String):Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    public fun validar2Senhas (pass1:String, pass2:String):Boolean {
        if (pass1.equals(pass2)){
            return true
        }
        return false
    }
}