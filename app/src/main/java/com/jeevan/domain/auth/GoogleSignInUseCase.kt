package com.jeevan.domain.auth

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.jeevan.di.MainDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.hardware.network.AuthInterceptor
import com.jeevan.mutation.CreateUserMutation
import com.jeevan.queries.GetWorkspaceCountQuery
import com.jeevan.utils.Constants
import com.jeevan.utils.suspendAndWait
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authInterceptor: AuthInterceptor,
    private val apolloClient: ApolloClient,
    @MainDispatcher dispatcher: CoroutineDispatcher
) :
    UseCase<AuthCredential, Unit>(dispatcher) {
    override suspend fun execute(parameters: AuthCredential) {
        val result = firebaseAuth.signInWithCredential(parameters).suspendAndWait()
        val token = result.user?.getIdToken(false)?.suspendAndWait()?.token!!
        authInterceptor.idToken = token

        // this is just a workaround but this is foul proof
        val workspacesCount =
            apolloClient.query(GetWorkspaceCountQuery()).await().data?.workspace?.size ?: 0
        if (workspacesCount == 0) {
            apolloClient.mutate(
                CreateUserMutation(
                    name = result.user?.displayName ?: "New User",
                    workspaceName = "${result?.user?.displayName ?: "Your User"} Workspace"
                )
            ).await()
        }
    }

}