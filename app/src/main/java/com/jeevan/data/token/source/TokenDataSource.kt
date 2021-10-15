package com.jeevan.data.token.source

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.jeevan.fragment.Workspace_token
import com.jeevan.queries.GetWorkspaceTokenQuery
import javax.inject.Inject

class TokenDataSource @Inject constructor(private val apolloClient: ApolloClient) {
    suspend fun getTokenData(tokenId: String): Workspace_token {
        // a little unsafe must be done in backend
        val response = apolloClient.query(GetWorkspaceTokenQuery(tokenId)).await()
        return response.data?.token?.fragments?.workspace_token!!
    }
}