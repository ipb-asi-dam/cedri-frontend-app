package com.example.cedri_app

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.cedri_app.model.*
import kotlinx.android.synthetic.main.activity_author_perfil.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.BitmapFactory
import android.graphics.Bitmap

class AuthorPerfilActivity : AppCompatActivity() {

    val REQUEST_PHONE_CALL = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_perfil)

        val token = NetworkUtils.getTokenFromDB(this)
        tryGetData(this, token)

        backImageButtonAutorPerfil.setOnClickListener {
            val intent = Intent(this, ArticleReviewActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
            finish()
        }

        author_phone_number.setOnClickListener {

            // TODO: COLOCAR ALGUM POPUP INDICANDO AO USUARIO Q ELE SERÁ ENCAMINHADO AO APP DE CHAMADAS E SE ELE ACEITAR ELE VAI PARA LIGAÇÃO
            // SEGUE LINK PARA UMA POSSIVEL SOLUCAO https://www.youtube.com/watch?v=6_kGW103Ue8 ESTA SOLUÇÂO CONSISTE EM COPIAR O NUMERO PARA O APLICATIVO DE DISCAGEM

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            } else {
                startCall()
            }
        }
    }

    private fun tryGetData(mainAct : AuthorPerfilActivity, token : String) {
        val payload = Decode.decoded(token)

        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.showInvestigator(payload.id)
        callback.enqueue(object : Callback<AuthenticateResponse<Investigator>> {
            override fun onFailure(call: Call<AuthenticateResponse<Investigator>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<AuthenticateResponse<Investigator>>, response: Response<AuthenticateResponse<Investigator>>) {
                val responseChecker = ResponseChecker(mainAct, response)

                if ( responseChecker.checkResponse() ) {
                    val investigator : Investigator = response.body()?.getData() ?: run {
                        val errorMsg = "INVESTIGADOR NÃO ENCONTRADO"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (investigator.file != null && investigator.file.mimetype.contains("image/")) {
                        tryGetImage(token, investigator.file.md5, mainAct, investigator)
                    } else {
                        configureInvestigator(investigator, null)
                    }

                }
            }
        })
    }

    private fun tryGetImage(token : String, md5 : String, mainAct : AuthorPerfilActivity, investigator: Investigator) {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getFile(md5)
        callback.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val imageInByte : ResponseBody = response.body() ?: run {
                        val errorMsg = "INVESTIGADOR NÃO ENCONTRADO"
                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                        return
                    }

                val imageInBitmap = BitmapFactory.decodeStream(imageInByte.byteStream())
                configureInvestigator(investigator, imageInBitmap)
            }
        })
    }

    private fun configureInvestigator(investigator : Investigator, image : Bitmap?) {
        author_perfil_first_email.text = investigator.email
        author_perfil_full_name.text = investigator.name
        image?.let {
            author_perfil_avatar.setImageBitmap(image)
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
