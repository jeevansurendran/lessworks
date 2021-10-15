package com.jeevan.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jeevan.data.token.model.TokenState
import com.jeevan.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val newWorkspaceUserViewModel: NewWorkspaceUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupObservers(binding)

    }

    private fun setupObservers(binding: ActivityMainBinding) {
        newWorkspaceUserViewModel.result.observe(this) {
            if (it.isSuccess) {
                it.getOrNull()?.let {
                    val message = when (it) {
                        TokenState.EXPIRED -> {
                            "This Link has expired, please request a new URL from the workspace owner"
                        }
                        TokenState.VALID -> {
                            "User Added to workspace, Switch to your new workspace."
                        }
                        TokenState.ALREADY_PRESENT -> {
                            "User Already present in the workspace."
                        }
                    }

                    Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
        intent.getStringExtra(TOKEN_ID)?.let {
            newWorkspaceUserViewModel.addUserWithToken(it)
        }
    }

    companion object {
        private const val TOKEN_ID = "main.token.id"
        fun launchMain(context: Context): Intent = Intent(context, MainActivity::class.java)

        fun launchMain(context: Context, tokenId: String) = launchMain(context).apply {
            putExtra(TOKEN_ID, tokenId)
        }
    }
}