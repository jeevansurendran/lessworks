package com.jeevan.data.user.source

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.jeevan.SearchUserQuery
import com.jeevan.SearchUserQuery.User
import javax.inject.Inject

class UserDataSource @Inject constructor(private val apolloClient: ApolloClient) {
    suspend fun searchUsers(query: String): List<User> {
        val response = apolloClient.query(SearchUserQuery("%$query%")).await()
        return response.data?.user ?: emptyList()
    }
}