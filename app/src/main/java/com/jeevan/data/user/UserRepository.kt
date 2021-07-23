package com.jeevan.data.user

import com.jeevan.SearchUserQuery
import com.jeevan.data.user.model.FirebaseUserInfo
import com.jeevan.data.user.source.FirebaseUserDataSource
import com.jeevan.data.user.source.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository
    @Inject constructor(
        private val firebaseUserDataSource: FirebaseUserDataSource,
        private val userDataSource: UserDataSource) {

    fun getFlowFirebaseUser(): Flow<FirebaseUserInfo> {
        return firebaseUserDataSource.getFlowFirebaseUser()
    }

    suspend fun searchUsers(query: String): List<SearchUserQuery.User> {
        return userDataSource.searchUsers(query)
    }

}