package com.jeevan.domain.auth

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.jeevan.di.MainDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.mutation.CreateUserMutation
import com.jeevan.queries.GetWorkspaceCountQuery
import com.jeevan.utils.Constants
import com.jeevan.utils.suspendAndWait
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @MainDispatcher dispatcher: CoroutineDispatcher
) :
    UseCase<AuthCredential, Unit>(dispatcher) {
    override suspend fun execute(parameters: AuthCredential) {
        val result = firebaseAuth.signInWithCredential(parameters).suspendAndWait()
        val token = result.user?.getIdToken(false)?.suspendAndWait()?.token!!

        // this is just a workaround but this is foul proof
        val okHttpClient = OkHttpClient.Builder().addInterceptor {
            val request = it.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer $token"
                )
                .build()
            it.proceed(request)
        }.build()

        val apolloClient = ApolloClient
            .builder()
            .serverUrl(Constants.ATA_GRAPHQL_ENDPOINT)
            .okHttpClient(okHttpClient)
            .build()

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