package com.jeevan.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.jeevan.R
import com.jeevan.ui.auth.AuthActivity
import com.jeevan.ui.main.MainActivity
import com.jeevan.utils.suspendAndWait
import com.jeevan.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()

    @Inject
    lateinit var dynamicLinks: FirebaseDynamicLinks

    @Inject
    lateinit var crashlytics: FirebaseCrashlytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupObservers()

    }

    private fun setupObservers() {
        splashViewModel.isSignedIn.observe(this) {
            if (it.isSuccess) {
                val isSignedIn = it.getOrDefault(false)
                var intent: Intent? = null
                lifecycleScope.launchWhenStarted {
                    intent = getIntent(isSignedIn)
                }.invokeOnCompletion {
                    startActivity(intent)
                    finish()
                }

            } else {
                toast("There has been a major error. Try again!")
            }
        }
    }

    private suspend fun getIntent(isSignedIn: Boolean): Intent {
        try {
            val pendingDynamicLinkData =
                dynamicLinks.getDynamicLink(this.intent)
                    .suspendAndWait()

            val tokenId = pendingDynamicLinkData?.link?.let {
                val deepLinkUrl = it.toString()
                deepLinkUrl.substring(deepLinkUrl.lastIndexOf("/") + 1)
            }
            return if (isSignedIn) {
                getMainIntent(tokenId)
            } else {
                getAuthIntent(tokenId)
            }
        } catch (e: Exception) {
            crashlytics.recordException(e)
            return getAuthIntent()
        }
    }

    private fun getAuthIntent(tokenId: String? = null): Intent {
        return tokenId?.let { AuthActivity.launchAuth(this, it) } ?: AuthActivity.launchAuth(this)

    }

    private fun getMainIntent(tokenId: String? = null): Intent {
        return tokenId?.let { MainActivity.launchMain(this, it) } ?: MainActivity.launchMain(this)

    }
}