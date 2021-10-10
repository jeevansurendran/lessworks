package com.jeevan.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.jeevan.databinding.ActivityAuthBinding
import com.jeevan.domain.auth.GetGoogleClientUseCase
import com.jeevan.domain.auth.GoogleSignInUseCase
import com.jeevan.ui.main.MainActivity
import com.jeevan.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<AuthViewModel>()

    @Inject
    internal lateinit var googleClient: GetGoogleClientUseCase
    @Inject
    internal lateinit var googleSignInUseCase: GoogleSignInUseCase

    private val signInResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account)
                    // send result here
                    return@registerForActivityResult
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Timber.w(e, "Google sign in failed")
                    toast("Google SignIn failed")
                }

            } else {
                Timber.w("Google sign in failed")
                toast("Google SignIn failed")
            }
            // if its not successful in any step its sets back to return
            setSignInEnabled()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.mcvAuthGoogle.setOnClickListener {
            try {
                signIn()
            } catch (e: Exception) {
                toast("There seems to be an error signing in, try again later.")
                setSignInEnabled()
            }
        }
    }

    private fun signIn() {
        if (viewModel.signInClicked.compareAndSet(false, true)) {
            binding.mcvAuthGoogle.isEnabled = !viewModel.signInClicked.get()
            val googleSignInAttempt = googleClient(Unit)
            if (googleSignInAttempt.isSuccess) {
                val intent = googleSignInAttempt.getOrThrow().signInIntent
                signInResultLauncher.launch(intent)
            } else {
                toast("There seems to be an error signing in, try again later.")
            }
        }
    }

    private fun setSignInEnabled() {
        viewModel.signInClicked.set(false)
        binding.mcvAuthGoogle.isEnabled = true
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)

        lifecycleScope.launch {
            googleSignInUseCase(credential)
            launchMain()
        }
    }

    private fun launchMain() {
        val intent = MainActivity.launchMain(this@AuthActivity)
        startActivity(intent)
        finish()
    }

    companion object {
        fun launchAuth(context: Context): Intent = Intent(context, AuthActivity::class.java)
    }
}