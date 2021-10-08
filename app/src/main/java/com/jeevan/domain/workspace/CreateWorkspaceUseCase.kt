package com.jeevan.domain.workspace

import com.jeevan.data.workspace.WorkspaceRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CreateWorkspaceUseCase @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, String>(dispatcher) {
    override suspend fun execute(parameters: String): String {
        return workspaceRepository.createWorkspace(parameters)
    }
}