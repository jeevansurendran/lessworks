package com.jeevan.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    val signInClicked = AtomicBoolean(false)
    val signInResult = MutableLiveData(Result.success(false)) as LiveData<*>

    private fun signInUser(account: GoogleSignInAccount) {

    }
}