package com.jeevan.domain.auth

import com.jeevan.data.user.UserRepository
import com.jeevan.di.MainDispatcher
import com.jeevan.domain.FlowUseCase
import com.jeevan.hardware.network.AuthInterceptor
import com.jeevan.utils.suspendAndWait
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAuthUserFlowCase @Inject constructor(
    @MainDispatcher dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
    private val authInterceptor: AuthInterceptor
) : FlowUseCase<Unit, Boolean>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Boolean> {
        return userRepository.getFlowFirebaseUser().map {
            if (it.isSignedIn()) {
                val tokenResult = it.firebaseUser?.getIdToken(false)?.suspendAndWait()
                if (tokenResult?.token.isNullOrBlank()) {
                    return@map false
                }
                // no idea whe
                authInterceptor.idToken = tokenResult?.token!!
            }
            it.isSignedIn()
        }
    }

}