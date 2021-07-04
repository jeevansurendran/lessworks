package com.jeevan.domain.workspace

import com.jeevan.GetWorkspacesQuery.Workspace
import com.jeevan.data.workspace.WorkspaceRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetWorkspacesUseCase @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<Workspace>>(dispatcher) {
    override suspend fun execute(parameters: Unit): List<Workspace> {
        return workspaceRepository.getWorkspacesRepository()
    }
}