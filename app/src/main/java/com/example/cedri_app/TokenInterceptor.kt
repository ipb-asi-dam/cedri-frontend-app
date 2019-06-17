package com.example.cedri_app


import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor constructor(private val token:String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        if ( original.url().encodedPath().contains("/login") &&  original.method().equals("post") ||
            (original.url().encodedPath().contains("/sign-up") && original.method().equals("post"))
        ) {
            return  chain.proceed(original)
        }

        val originalHttpUrl = original.url()
        val requestBuilder = original.newBuilder()
            .addHeader("Authorization",token)
            .url(originalHttpUrl)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}