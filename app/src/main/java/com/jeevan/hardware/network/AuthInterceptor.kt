package com.jeevan.hardware.network

import com.jeevan.data.user.source.FirebaseUserDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor(firebaseUserDataSource: FirebaseUserDataSource) : Interceptor {
    var idToken = ""
    init {
        GlobalScope.launch {
            firebaseUserDataSource.getFlowFirebaseIdToken().collect {
                if (it != null) {
                    idToken = it
                }
            }
        }
    }
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