package com.jeevan.di

import com.apollographql.apollo.ApolloClient
import com.jeevan.data.user.source.FirebaseUserDataSource
import com.jeevan.hardware.network.AuthInterceptor
import com.jeevan.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(firebaseUserDataSource: FirebaseUserDataSource): AuthInterceptor {
        return AuthInterceptor(firebaseUserDataSource)
    }

    @Singleton
    @Provides
    fun provideApolloClient(authInterceptor: AuthInterceptor): ApolloClient {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(authInterceptor).build()
        return ApolloClient.builder()
            .serverUrl(Constants.ATA_GRAPHQL_ENDPOINT)
            .okHttpClient(okHttpClient)
            .build()
    }
}