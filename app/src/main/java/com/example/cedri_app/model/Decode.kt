package com.example.cedri_app.model

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import kotlin.text.Charsets.UTF_8

class Decode {
    companion object {
        fun decoded(JWTEncoded: String): Payload {
            var split: Array<String>? = null
            try {
                split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                Log.d("JWT_DECODED", "Header: " + getJson(split!![0]))
                Log.d("JWT_DECODED", "Body: " + getJson(split!![1]))
                Log.d("JWT_DECODED", "Signiture: " + getJson(split!![2]))
            } catch (e: UnsupportedEncodingException) {
                //Error
            }

            val converted = getJson(split!![1])

            val regex = """\{"id":(\d+),"isAdmin":(?:(true|false)),"iat":(\d+),"exp":(\d+)\}""".toRegex()
            val matchResult = regex.find(converted)
            val (id, isAdmin, iat, exp) = matchResult!!.destructured

            val payload : Payload = Payload(id.toInt(), isAdmin.toBoolean(), iat.toInt(), exp.toInt())
            return payload
        }

        private fun getJson(strEncoded: String): String {
            val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
            return String(decodedBytes, UTF_8)
        }
    }

}

