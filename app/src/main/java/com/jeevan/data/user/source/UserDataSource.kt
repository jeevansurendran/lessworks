package com.jeevan.data.user.source

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.jeevan.queries.SearchUserQuery
import javax.inject.Inject

class UserDataSource @Inject constructor(private val apolloClient: ApolloClient) {
    suspend fun searchUsers(query: String): List<SearchUserQuery.User> {
        val response = apolloClient.query(SearchUserQuery("%$query%")).await()
        return response.data?.user ?: emptyList()
    }
}