package com.jeevan

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "x-hasura-admin-secret",
                "lCQjGurNMnXi8ILYNreZTHVkpSNOnwFIkLKiG6JCZ0Q4CU0EKFFa65TFuB4MdsSp"
            )
            .build()

        return chain.proceed(request)
    }
}