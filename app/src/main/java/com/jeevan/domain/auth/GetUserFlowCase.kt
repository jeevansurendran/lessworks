package com.jeevan.domain.auth

import com.jeevan.data.user.UserRepository
import com.jeevan.data.user.model.FirebaseUserInfo
import com.jeevan.di.MainDispatcher
import com.jeevan.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserFlowCase @Inject constructor(
    @MainDispatcher dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : FlowUseCase<Unit, FirebaseUserInfo>(dispatcher) {
    override fun execute(parameters: Unit): Flow<FirebaseUserInfo> {
        return userRepository.getFlowFirebaseUser()
    }
}