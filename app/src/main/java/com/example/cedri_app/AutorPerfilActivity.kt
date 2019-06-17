package com.example.cedri_app

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_autor_perfil.*
import java.util.jar.Manifest

class AutorPerfilActivity : AppCompatActivity() {

    val REQUEST_PHONE_CALL = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autor_perfil)

        backImageButtonAutorPerfil.setOnClickListener {
            val intent = Intent(this, ArticleReviewActivity::class.java)
            startActivity(intent)
            finish()
        }

        authorNum.setOnClickListener {

            // TODO: COLOCAR ALGUM POPUP INDICANDO AO USUARIO Q ELE SERÁ ENCAMINHADO AO APP DE CHAMADAS E SE ELE ACEITAR ELE VAI PARA LIGAÇÃO
            // SEGUE LINK PARA UMA POSSIVEL SOLUCAO https://www.youtube.com/watch?v=6_kGW103Ue8 ESTA SOLUÇÂO CONSISTE EM COPIAR O NUMERO PARA O APLICATIVO DE DISCAGEM

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            } else {
                startCall()
            }
        }
    }

    private fun startCall(){
        val callIntent = Intent(Intent.ACTION_CALL)
        val number = "913801231"

        callIntent.data = Uri.parse("tel:" + number)
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PHONE_CALL){
            startCall()
        }
    }
}
