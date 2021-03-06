package com.jeevan.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.internal.ApolloLogger
import com.jeevan.data.user.source.FirebaseUserDataSource
import com.jeevan.hardware.network.AuthInterceptor
import com.jeevan.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    fun provideApolloClient(authInterceptor: AuthInterceptor): ApolloClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
        return ApolloClient.builder()
            .serverUrl(Constants.ATA_GRAPHQL_ENDPOINT)
            .okHttpClient(okHttpClient)
            .build()
    }
}