package com.jeevan.domain.share

import android.net.Uri
import com.jeevan.data.dynamiclinks.DynamicLinkSource
import com.jeevan.data.workspace.WorkspaceRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.fragment.Workspace_token
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ShareWorkspaceUseCase @Inject constructor(
    private val dynamicLinkSource: DynamicLinkSource,
    private val workspaceRepository: WorkspaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, Pair<String, Workspace_token>>(dispatcher) {
    override suspend fun execute(parameters: String): Pair<String, Workspace_token> {
        val token = workspaceRepository.createShareToken(parameters)
        val uri = Uri.Builder().apply {
            scheme("https")
            authority("lessworks.com")
            appendPath("invites")
            appendPath(token.id as String)
        }.build()

        val url = dynamicLinkSource.createShareableLink(uri).toString()
        return url to token
    }
}

