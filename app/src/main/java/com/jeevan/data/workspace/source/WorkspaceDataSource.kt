package com.jeevan.data.workspace.source

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.jeevan.GetWorkspacesQuery
import javax.inject.Inject

class WorkspaceDataSource @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getWorkspacesData(): List<GetWorkspacesQuery.Workspace> {
        val response = apolloClient.query(GetWorkspacesQuery()).await()
        return response.data?.workspace ?: emptyList()
    }

}