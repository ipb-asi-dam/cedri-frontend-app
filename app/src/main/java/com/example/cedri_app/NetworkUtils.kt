package com.example.cedri_app
import android.content.Context
import android.os.Bundle
import com.example.cedri_app.database.DatabaseHandler
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    }
}