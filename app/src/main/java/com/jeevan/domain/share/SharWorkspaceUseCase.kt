package com.jeevan.domain.share

import android.net.Uri
import com.jeevan.data.dynamiclinks.DynamicLinkSource
import com.jeevan.data.workspace.WorkspaceRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.utils.Formatter
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SharWorkspaceUseCase @Inject constructor(
    private val dynamicLinkSource: DynamicLinkSource,
    private val workspaceRepository: WorkspaceRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, String>(dispatcher) {
    override suspend fun execute(parameters: String): String {
        val token = workspaceRepository.createShareToken(parameters)
        val workspaceName = token.workspace.name
        val uri = Uri.Builder().apply {
            scheme("https")
            authority("lessworks.com")
            appendPath("invites")
            appendPath(token.id as String)
        }.build()

        val url = dynamicLinkSource.createShareableLink(uri)
        return "You have been invited to workspace '$workspaceName'." +
                " Use the below link to join the workspace. Link expires " +
                "by ${Formatter.formatDurationISO(token.expires_at as String)}.\n\n\nJoin now: $url"
    }
}

