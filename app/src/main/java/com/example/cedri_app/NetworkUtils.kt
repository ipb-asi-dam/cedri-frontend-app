package com.example.cedri_app
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.example.cedri_app.database.DatabaseHandler
import com.example.cedri_app.model.AuthenticateResponse
import com.example.cedri_app.model.Decode
import com.example.cedri_app.model.Investigator
import com.example.cedri_app.model.ResponseChecker
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {

    companion object {
        fun getRetrofit(token: String) : Retrofit {
            val tokenInterceptor = TokenInterceptor(token)

            return setupRetrofit(tokenInterceptor, getBaseUrl())
        }

        /** Retorna uma instancia do Cliente Retrofit para requisições.
         * @param path caminho principal da API
         */
        fun setupRetrofit(tokenInterceptor : TokenInterceptor, baseUrl : String): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okhttpBuilder = OkHttpClient().newBuilder().addInterceptor(interceptor).addInterceptor(tokenInterceptor)
            val client= okhttpBuilder.build()
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            val gson =  gsonBuilder.create()
            return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build()
        }

        fun getBaseUrl() : String {
            return "http://194.210.91.11:5000"
        }

        fun getToken(extras : Bundle?) : String {
            var token = ""
            extras?.getString("token")?.let {
                token = it
            }
            return token
        }

        fun getTokenFromDB(context: Context) : String {
            val myDB = DatabaseHandler(context)
            val cursorDatabase = myDB.getTokenFromDatabase()
            return if (cursorDatabase.moveToFirst())
                cursorDatabase.getString(cursorDatabase.getColumnIndex("token"))
            else ""
        }


        fun setupAvatar(mainAct : Context, token : String, imgButton : ImageButton) {
            val payload = Decode.decoded(token)
            val retrofitClient = NetworkUtils.getRetrofit(token)
            val endpoint = retrofitClient.create(Endpoint::class.java)
            val callback = endpoint.showInvestigator(payload.id)

            callback.enqueue(object : Callback<AuthenticateResponse<Investigator>> {
                override fun onFailure(call: Call<AuthenticateResponse<Investigator>>, t: Throwable) =
                    Toast.makeText(mainAct, t.message, Toast.LENGTH_SHORT).show()

                override fun onResponse(call: Call<AuthenticateResponse<Investigator>>, response: Response<AuthenticateResponse<Investigator>>) {
                    val responseChecker = ResponseChecker(mainAct, response)
                    if (responseChecker.checkResponse()) {
                        val investigator: Investigator = response.body()?.getData() ?: run {
                            return Toast.makeText(mainAct, "INVESTIGADOR NÃO ENCONTRADO", Toast.LENGTH_SHORT).show()
                        }
                        if (investigator.file != null && investigator.file.mimetype.contains("image/")) {
                            //val retrofitClient = NetworkUtils.getRetrofit(token)
                            //val endpoint = retrofitClient.create(Endpoint::class.java)
                            val callback2 = endpoint.getFile(investigator.file.md5)

                            callback2.enqueue(object : Callback<ResponseBody> {
                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) =
                                    Toast.makeText(mainAct, t.message, Toast.LENGTH_SHORT).show()

                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    val imageInByte : ResponseBody = response.body() ?: run {
                                        val errorMsg = "INVESTIGADOR NÃO ENCONTRADO"
                                        return Toast.makeText(mainAct, errorMsg, Toast.LENGTH_SHORT).show()
                                    }
                                    imgButton.setImageBitmap(BitmapFactory.decodeStream(imageInByte.byteStream()))
                                }
                            })
                        }
                    }
                }
            })
        }
    }
}
