package com.jeevan.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.jeevan.GetHarshithaQuery
import com.jeevan.R
import com.jeevan.hardware.network.AuthorizationInterceptor
import com.jeevan.utils.toast
import okhttp3.OkHttpClient

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // First, create an `ApolloClient`
// Replace the serverUrl with your GraphQL endpoint
        val apolloClient = ApolloClient.builder()
            .okHttpClient(
                OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor())
                .build()
            )
            .serverUrl("https://ata.hasura.app/v1/graphql")
            .build()

// in your coroutine scope, call `ApolloClient.query(...).toDeferred().await()`
        lifecycleScope.launchWhenStarted {
            val response = try {
                apolloClient.query(GetHarshithaQuery()).await()
            } catch (e: ApolloException) {
                // handle protocol errors
                return@launchWhenStarted
            }

            val name = response.data?.user?.get(0)?.name
            if (name == null || response.hasErrors()) {
                // handle application errors
                toast("Sleep sleep sleep")
            }

            toast("Hottest girl on the planet: $name")
        }
    }
}