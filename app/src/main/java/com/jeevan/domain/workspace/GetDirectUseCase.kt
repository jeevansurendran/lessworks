package com.jeevan.domain.workspace

import com.jeevan.data.user.UserRepository
import com.jeevan.data.workspace.WorkspaceRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.fragment.Direct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetDirectUseCase @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val userRepository: UserRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Pair<String, String>, Direct>(dispatcher) {
    override suspend fun execute(parameters: Pair<String, String>): Direct {
        val userIdMe = userRepository.getFlowFirebaseUser().first().getUid()!!
        return workspaceRepository.getDirect(parameters.first, userIdMe, parameters.second)
    }

}