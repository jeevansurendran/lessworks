package com.jeevan.hardware.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "x-hasura-admin-secret",
                 "lCQjGurNMnXi8ILYNreZTHVkpSNOnwFIkLKiG6JCZ0Q4CU0EKFFa65TFuB4MdsSp"
            )
            .build()
        Timber.d("WAITTTTTTT WHATTTTTT")
        return chain.proceed(request)
    }
}