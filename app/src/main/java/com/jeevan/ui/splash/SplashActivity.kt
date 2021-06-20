package com.jeevan.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jeevan.R
import com.jeevan.ui.auth.AuthActivity
import com.jeevan.ui.main.MainActivity
import com.jeevan.utils.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupObservers()

    }

    private fun setupObservers() {
        splashViewModel.isSignedIn.observe(this) {
            if (it.isSuccess) {
                if (it.getOrDefault(false)) {
                    launchMain()
                } else {
                    launchAuth()
                }
            } else {
                toast("There has been a major error. Try again!")
            }
        }
    }

    private fun launchAuth() {
        val intent = AuthActivity.launchAuth(this)
        startActivity(intent)
        finish()
    }

    private fun launchMain() {
        val intent = MainActivity.launchMain(this)
        startActivity(intent)
        finish()
    }
}