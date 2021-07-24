package com.jeevan.data.workspace

import com.jeevan.data.workspace.source.WorkspaceDataSource
import com.jeevan.fragment.Group
import com.jeevan.queries.GetWorkspacesQuery
import javax.inject.Inject

class WorkspaceRepository @Inject constructor(private val workspaceDataSource: WorkspaceDataSource) {
    suspend fun getWorkspacesRepository(): List<GetWorkspacesQuery.Workspace> {
        return workspaceDataSource.getWorkspacesData()
    }

    suspend fun addGroup(workspaceId: String, name: String, users: List<String>): Group {
        return workspaceDataSource.addGroup(workspaceId, name, users)
    }
}