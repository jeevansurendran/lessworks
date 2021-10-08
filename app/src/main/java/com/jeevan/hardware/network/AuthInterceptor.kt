package com.jeevan.hardware.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    var idToken = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                 "Bearer $idToken"
            )
            .build()
        return chain.proceed(request)
    }
}