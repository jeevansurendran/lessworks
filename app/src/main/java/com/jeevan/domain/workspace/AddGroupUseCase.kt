package com.jeevan.domain.workspace

import com.jeevan.data.user.UserRepository
import com.jeevan.data.workspace.WorkspaceRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val workspaceRepository: WorkspaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Triple<String, String, MutableSet<String>>, String>(dispatcher) {
    override suspend fun execute(parameters: Triple<String, String, MutableSet<String>>): String {
        val uid = userRepository.getFlowFirebaseUser().first().firebaseUser?.uid
        if (uid != null) {
            parameters.third.add(uid)
        }
        return workspaceRepository.addGroup(
            parameters.first,
            parameters.second,
            parameters.third.toList()
        )
    }
}