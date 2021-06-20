package com.jeevan.data.user

import com.jeevan.data.user.model.FirebaseUserInfo
import com.jeevan.data.user.source.FirebaseUserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(private val firebaseUserDataSource: FirebaseUserDataSource) {

    fun getFlowFirebaseUser(): Flow<FirebaseUserInfo> {
        return firebaseUserDataSource.getFlowFirebaseUser()
    }

}