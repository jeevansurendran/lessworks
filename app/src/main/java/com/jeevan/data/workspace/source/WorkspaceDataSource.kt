package com.jeevan.data.workspace.source

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.jeevan.GetWorkspacesQuery
import com.jeevan.InsertGroupMutation
import com.jeevan.type.Group_user_insert_input
import javax.inject.Inject

class WorkspaceDataSource @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getWorkspacesData(): List<GetWorkspacesQuery.Workspace> {
        val response = apolloClient.query(GetWorkspacesQuery()).await()
        return response.data?.workspace ?: emptyList()
    }

    suspend fun addGroup(workspaceId: String, name: String, users: List<String>): String {
        val response = apolloClient.mutate(
            InsertGroupMutation(
                workspaceId,
                Input.fromNullable(name),
                users.map { Group_user_insert_input(user_id = Input.fromNullable(it)) })
        ).await()
        return response.data?.insert_group_one?.id as String
    }

}