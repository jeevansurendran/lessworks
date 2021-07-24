package com.jeevan.domain.workspace

import com.jeevan.data.workspace.WorkspaceRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.queries.GetGroupQuery
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetGroupUseCase @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, GetGroupQuery.Group>(dispatcher) {
    override suspend fun execute(parameters: String): GetGroupQuery.Group {
        return workspaceRepository.getGroup(parameters)
    }

}