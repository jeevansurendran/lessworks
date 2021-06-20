package com.jeevan.domain.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jeevan.R
import com.jeevan.domain.BaseUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetGoogleClientUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) :
    BaseUseCase<Unit, GoogleSignInClient>() {
    override fun execute(parameters: Unit): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

}