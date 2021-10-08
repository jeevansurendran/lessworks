package com.jeevan.data.workspace.source

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.jeevan.fragment.Direct
import com.jeevan.fragment.Group
import com.jeevan.mutation.CreateDirectMutation
import com.jeevan.mutation.CreateWorkspaceMutation
import com.jeevan.mutation.InsertGroupMutation
import com.jeevan.queries.DirectTaskQuery
import com.jeevan.queries.GetGroupQuery
import com.jeevan.queries.GetWorkspacesQuery
import com.jeevan.type.Group_user_insert_input
import javax.inject.Inject

class WorkspaceDataSource @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getWorkspacesData(): List<GetWorkspacesQuery.Workspace> {
        val response = apolloClient.query(GetWorkspacesQuery()).await()
        return response.data?.workspace ?: emptyList()
    }

    suspend fun addGroup(workspaceId: String, name: String, users: List<String>): Group {
        val response = apolloClient.mutate(
            InsertGroupMutation(
                workspaceId,
                Input.fromNullable(name),
                users.map { Group_user_insert_input(user_id = Input.fromNullable(it)) })
        ).await()
        return response.data?.insert_group_one?.fragments?.group!!
    }

    suspend fun getGroup(groupId: String): GetGroupQuery.Group {
        val response = apolloClient.query(
            GetGroupQuery(groupId)
        ).await()
        return response.data?.group!!
    }

    private suspend fun addDirect(workspaceId: String, userId1: String, userId2: String): Direct {
        val response = apolloClient.mutate(
            CreateDirectMutation(
                workspaceId,
                Input.fromNullable(userId1),
                Input.fromNullable(userId2)
            )
        ).await()
        return response.data?.insert_direct_workspace_user_one?.direct?.fragments?.direct!!
    }

    suspend fun getDirect(workspaceId: String, userId1: String, userId2: String): Direct {
        val response = apolloClient.query(
            DirectTaskQuery(
                workspaceId,
                user1 = Input.fromNullable(userId1),
                user2 = Input.fromNullable(userId2)
            )
        ).await()
        if (response.data?.direct_workspace_user?.size!! == 0) {
            return addDirect(workspaceId, userId1, userId2)
        }

        return response.data?.direct_workspace_user?.get(0)?.direct?.fragments?.direct!!
    }

    suspend fun createWorkspace(name: String): String {
        val response = apolloClient.mutate(
            CreateWorkspaceMutation(
                name
            )
        ).await()
        return response.data?.insert_workspace_one?.id!! as String
    }


}