package com.jeevan.domain.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.jeevan.di.MainDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.utils.suspendAndWait
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @MainDispatcher dispatcher: CoroutineDispatcher
) :
    UseCase<AuthCredential, Unit>(dispatcher) {
    override suspend fun execute(parameters: AuthCredential) {
        firebaseAuth.signInWithCredential(parameters).suspendAndWait()
    }

}