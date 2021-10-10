package com.jeevan.data.workspace

import com.jeevan.data.workspace.source.WorkspaceDataSource
import com.jeevan.fragment.Direct
import com.jeevan.fragment.Group
import com.jeevan.fragment.Workspace_token
import com.jeevan.queries.GetGroupQuery
import com.jeevan.queries.GetWorkspacesQuery
import javax.inject.Inject

class WorkspaceRepository @Inject constructor(private val workspaceDataSource: WorkspaceDataSource) {
    suspend fun getWorkspacesRepository(): List<GetWorkspacesQuery.Workspace> {
        return workspaceDataSource.getWorkspacesData()
    }

    suspend fun addGroup(workspaceId: String, name: String, users: List<String>): Group {
        return workspaceDataSource.addGroup(workspaceId, name, users)
    }

    suspend fun getGroup(groupId: String): GetGroupQuery.Group {
        return workspaceDataSource.getGroup(groupId)
    }

    suspend fun getDirect(workspaceId: String, userId1: String, userId2: String): Direct {
        return workspaceDataSource.getDirect(workspaceId, userId1, userId2)
    }

    suspend fun createWorkspace(name: String): String {
        return workspaceDataSource.createWorkspace(name)
    }

    suspend fun createShareToken(id: String): Workspace_token {
        return workspaceDataSource.createShareToken(id)
    }
}