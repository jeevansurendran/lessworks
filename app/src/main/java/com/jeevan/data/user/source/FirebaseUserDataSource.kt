package com.jeevan.data.user.source

import com.google.firebase.auth.FirebaseAuth
import com.jeevan.data.user.model.FirebaseUserInfo
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import javax.inject.Inject

class FirebaseUserDataSource @Inject constructor(private val auth: FirebaseAuth) {
    fun getFlowFirebaseUser(): Flow<FirebaseUserInfo> {
        return channelFlow {
            val authStateListener: ((FirebaseAuth) -> Unit) = { auth ->
                if (isActive) {
                    auth.currentUser.let { it ->
                        channel.trySend(FirebaseUserInfo(it)).onClosed {
                            throw it ?: ClosedSendChannelException("Channel was closed normally")
                        }.isSuccess
                    }
                }
            }
            auth.addAuthStateListener(authStateListener)
            awaitClose {
                auth.removeAuthStateListener(authStateListener)
            }
        }
    }
}