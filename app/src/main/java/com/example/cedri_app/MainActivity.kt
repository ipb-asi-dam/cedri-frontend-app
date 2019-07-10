package com.example.cedri_app

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cedri_app.database.DatabaseHandler
import com.example.cedri_app.model.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    var myDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        buttonForgotPassword.setOnClickListener {
            val intent = Intent (this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
        */
        buttonLogin.setOnClickListener{
            val utilsTeste = Utils()
            if (editTextEmailActivityMain.text.isEmpty()) {
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
                val email = editTextEmailActivityMain.text.toString();
                val password = editTextPasswordActivityMain.text.toString();
                Toast.makeText(this,"CAMPOS CORRETOS", Toast.LENGTH_LONG).show()
                loginRequest(this, email, password)
            }
        }
    }

    private fun loginRequest(mainAct : MainActivity, email: String, password: String) {
        val tokenInterceptor = TokenInterceptor("TOKEN_FROM_LOGIN")
        val retrofitClient = NetworkUtils.setupRetrofit(tokenInterceptor, NetworkUtils.getBaseUrl())
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val authRequest = AuthenticateRequest(email, password)
        val callback = endpoint.postLogin(authRequest)

        callback.enqueue(object : Callback<AuthenticateResponse<Token>> {
            override fun onFailure(call: Call<AuthenticateResponse<Token>>, t: Throwable) =
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()

            override fun onResponse(call: Call<AuthenticateResponse<Token>>, response: Response<AuthenticateResponse<Token>>) =
                receivedTokenSuccessfully(mainAct, response)
        })
    }

    private fun receivedTokenSuccessfully(mainAct: MainActivity, response: Response<AuthenticateResponse<Token>>) {
        val responseChecker = ResponseChecker(mainAct, response)

        if ( responseChecker.checkResponse() ) {
            val token = response.body()?.getData()?.token
            //armazenar no banco o token (ele só passa pra proxima tela se ele receber e inserir o token no DB)
            if ( !token.isNullOrBlank() ) {
                return tryInsertTokenInDB(mainAct, token)
            }
            Toast.makeText(baseContext, "Não recebido o token pelo servidor", Toast.LENGTH_SHORT).show()
        }
    }

    private fun tryInsertTokenInDB(mainAct: MainActivity, token : String) {
        if( myDB.insertToken(token) ) {
            /*
            Toast.makeText(baseContext, "Inserido no DB", Toast.LENGTH_SHORT).show()
            return tryGetData(mainAct, token)
            */
            Toast.makeText(baseContext, "Inserido no DB", Toast.LENGTH_SHORT).show()
            return goToMenu(mainAct)
        }
        return Toast.makeText(baseContext, "Não inserido no DB", Toast.LENGTH_SHORT).show()
    }

    private fun tryGetData(mainAct : MainActivity, token : String) {
        val payload = Decode.decoded(token)
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.showInvestigator(payload.id)

        callback.enqueue(object : Callback<AuthenticateResponse<Investigator>> {
            override fun onFailure(call: Call<AuthenticateResponse<Investigator>>, t: Throwable) =
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()

            override fun onResponse(call: Call<AuthenticateResponse<Investigator>>, response: Response<AuthenticateResponse<Investigator>>) =
                receivedInvestigatorSuccessfully(mainAct, response, token)
        })
    }

    private fun receivedInvestigatorSuccessfully(mainAct : MainActivity, response: Response<AuthenticateResponse<Investigator>>, token : String) {
        val responseChecker = ResponseChecker(mainAct, response)
        if ( responseChecker.checkResponse() ) {
            val investigator : Investigator = response.body()?.getData() ?: run {
                return Toast.makeText(baseContext, "INVESTIGADOR NÃO ENCONTRADO", Toast.LENGTH_SHORT).show()
            }
            if (investigator.file != null && investigator.file.mimetype.contains("image/")) {
                tryGetImage(token, investigator.file.md5, mainAct)
            } else {
                goToMenu(mainAct)
            }
        } else {
            goToMenu(mainAct)
        }
    }

    private fun tryGetImage(token : String, md5 : String, mainAct: MainActivity) {
        val retrofitClient = NetworkUtils.getRetrofit(token)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getFile(md5)

        callback.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) =
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                receivedImageSuccessfully(mainAct, response, token)
        })
    }

    private fun receivedImageSuccessfully(mainAct : MainActivity, response: Response<ResponseBody>, token : String) {
        /*
        val imageInByteArray  = response.body()?.bytes() ?: run {
            val errorMsg = "INVESTIGADOR NÃO ENCONTRADO"
            Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
            return
        }*/

        val imageInByte : ResponseBody = response.body() ?: run {
            val errorMsg = "INVESTIGADOR NÃO ENCONTRADO"
            Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
            return
        }
        val imageInBitmap = BitmapFactory.decodeStream(imageInByte.byteStream())

        var bstream = ByteArrayOutputStream()
        imageInBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bstream)
        val img = bstream.toByteArray()

        if ( myDB.insertAvatar(img) ) {
            Toast.makeText(baseContext, "Avatar inserido no DB", Toast.LENGTH_SHORT).show()
            val cursorDatabase = myDB.getTokenFromDatabase()
            if (cursorDatabase.moveToFirst()) {
                val columnType = cursorDatabase.getType(cursorDatabase.getColumnIndex("avatar"))
                val columnIndex = cursorDatabase.getColumnIndex("avatar")
                val avatarInByteArray = cursorDatabase.getBlob(cursorDatabase.getColumnIndex("avatar"))
                Log.e("ARRAY BYTE", "############# $avatarInByteArray")
            } else {
                Log.e("ERRO", " ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRe")
            }
        }
        goToMenu(mainAct)
    }

    private fun goToMenu(mainAct : MainActivity) {
        val intent = Intent(mainAct, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}
