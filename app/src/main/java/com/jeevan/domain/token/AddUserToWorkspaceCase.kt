package com.jeevan.domain.token

import com.jeevan.data.token.TokenRepository
import com.jeevan.data.token.model.TokenState
import com.jeevan.data.user.UserRepository
import com.jeevan.data.workspace.WorkspaceRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.utils.Formatter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddUserToWorkspaceCase @Inject constructor(
    private val userRepository: UserRepository
    private val workspaceRepository: WorkspaceRepository,
    private val tokenRepository: TokenRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, TokenState>(dispatcher) {
    override suspend fun execute(parameters: String): TokenState {
        val tokenData = tokenRepository.getTokenData(parameters)
        val isExpired = Formatter.isExpiredDate(tokenData.expires_at as String)

        return if (!isExpired) {
            val uid = userRepository.getFlowFirebaseUser().first().firebaseUser?.uid
            val isNotPresent =
                workspaceRepository.addUserToWorkspace(tokenData.workspace_id as String, uid!!)
            if (isNotPresent) TokenState.VALID else TokenState.ALREADY_PRESENT
        } else TokenState.EXPIRED
    }
}