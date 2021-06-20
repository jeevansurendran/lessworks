package com.jeevan.domain.auth

import com.jeevan.data.user.UserRepository
import com.jeevan.data.user.model.FirebaseUserInfo
import com.jeevan.di.MainDispatcher
import com.jeevan.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAuthUserFlowCase @Inject constructor(
    @MainDispatcher dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) : FlowUseCase<Unit, Boolean>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Boolean> {
        return userRepository.getFlowFirebaseUser().map { it.isSignedIn() }
    }

}