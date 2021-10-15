package com.jeevan.domain.workspace

import com.jeevan.data.user.UserRepository
import com.jeevan.data.workspace.WorkspaceRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreateWorkspaceUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val workspaceRepository: WorkspaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, String>(dispatcher) {
    override suspend fun execute(parameters: String): String {
        val userId = userRepository.getFlowFirebaseUser().first().getUid()!!
        return workspaceRepository.createWorkspace(parameters, userId)
    }
}