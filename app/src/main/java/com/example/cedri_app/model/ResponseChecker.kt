package com.example.cedri_app.model

import android.content.Context
import android.widget.Toast
import retrofit2.Response

class ResponseChecker<T>(activity: Context, response: Response<AuthenticateResponse<T>>?) {
    val activity = activity
    val response = response

    fun checkResponse() : Boolean {
        if( response !== null) {
            return hasResponse()
        }
        return showMsgAndReturnFalse("ERRO INESPERADO: RESPOSTA VAZIA")
    }

    private fun hasResponse() : Boolean {
        return if (response?.body() !== null) hasBody()
            else showMsgAndReturnFalse("OCORREU UM ERRO INESPERADO: SEM BODY")
    }

    private fun hasBody() : Boolean {
        return if (response?.body()?.getStatus() !== null) hasStatus()
            else showMsgAndReturnFalse("OCORREU UM ERRO INESPERADO: SEM STATUS")
    }

    private fun hasStatus() : Boolean {
        if (response?.body()?.getStatus() == "success") {
            return if (response?.body()?.getData() !== null) hasData()
                else showMsgAndReturnFalse("NÃO FORAM RECEBIDOS DADOS DO SERVIDOR. TENTE NOVAMENTE")
        }
        return showMsgAndReturnFalse("LOGIN OU SENHA INVÁLIDO")
    }

    private fun hasData() : Boolean {
        return if (response?.body()?.getData() != null) true else showMsgAndReturnFalse("DATA VAZIO. TENTE NOVAMENTE")
    }

    private fun showMsgAndReturnFalse(errorMsg: String) : Boolean {
        Toast.makeText(activity, errorMsg, Toast.LENGTH_LONG).show()
        return false
    }
}