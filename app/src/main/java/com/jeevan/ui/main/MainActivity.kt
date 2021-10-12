package com.jeevan.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeevan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {
        private const val TOKEN_ID = "main.token.id"
        fun launchMain(context: Context): Intent = Intent(context, MainActivity::class.java)

        fun launchMain(context: Context, tokenId: String) = Companion.launchMain(context).apply {
            putExtra(TOKEN_ID, tokenId)
        }
    }
}