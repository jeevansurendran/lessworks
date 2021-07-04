package com.jeevan.data.workspace

import com.jeevan.GetWorkspacesQuery.Workspace
import com.jeevan.data.workspace.source.WorkspaceDataSource
import javax.inject.Inject

class WorkspaceRepository @Inject constructor(private val workspaceDataSource: WorkspaceDataSource) {
    suspend fun getWorkspacesRepository(): List<Workspace> {
        return workspaceDataSource.getWorkspacesData()
    }
}