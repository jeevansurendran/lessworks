package com.jeevan.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toJson
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.jeevan.GetWorkspaceQuery
import com.jeevan.R
import com.jeevan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @Inject
    internal lateinit var apolloClient: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnMainFetch.setOnClickListener {
            lifecycleScope.launch {
                val response = try {
                    apolloClient.query(GetWorkspaceQuery()).await()
                } catch (e: ApolloException) {
                    // handle protocol errors
                    return@launch
                }

                val launch = response.data?.toJson(indent = "\t\t")
                if (launch == null || response.hasErrors()) {
                    // handle application errors
                    return@launch
                }
                Timber.d(launch)
            }
        }

    }

    companion object {
        fun launchMain(context: Context): Intent = Intent(context, MainActivity::class.java)
    }
}