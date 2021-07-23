package com.jeevan.data.workspace

import com.jeevan.GetWorkspacesQuery.Workspace
import com.jeevan.data.workspace.source.WorkspaceDataSource
import javax.inject.Inject

class WorkspaceRepository @Inject constructor(private val workspaceDataSource: WorkspaceDataSource) {
    suspend fun getWorkspacesRepository(): List<Workspace> {
        return workspaceDataSource.getWorkspacesData()
    }

    suspend fun addGroup(workspaceId: String, name: String, users: List<String>): String {
        return workspaceDataSource.addGroup(workspaceId, name, users)
    }
}