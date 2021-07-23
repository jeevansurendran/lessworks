package com.jeevan.domain.user

import com.jeevan.SearchUserQuery
import com.jeevan.data.user.UserRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.utils.Constants.GROUP_MEMBER_SEARCH_LENGTH
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) :
    UseCase<String, List<SearchUserQuery.User>>(dispatcher) {
    override suspend fun execute(parameters: String): List<SearchUserQuery.User> {
        if (parameters.length < GROUP_MEMBER_SEARCH_LENGTH) {
            return emptyList()
        }
        return userRepository.searchUsers(parameters)
    }
}