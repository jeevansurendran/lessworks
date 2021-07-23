package com.jeevan.ui.createGroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeevan.SearchUserQuery
import com.jeevan.domain.auth.GetAuthUserFlowCase
import com.jeevan.domain.user.SearchUsersUseCase
import com.jeevan.domain.workspace.AddGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupMembersViewModel @Inject constructor(
    private val searchUsers: SearchUsersUseCase,
    private val addGroup: AddGroupUseCase
) :
    ViewModel() {

    private val _searchUsersResult = MutableLiveData<Result<List<SearchUserQuery.User>>>()
    val searchUsersResult = _searchUsersResult as LiveData<Result<List<SearchUserQuery.User>>>

    private val _addGroupResult = MutableLiveData<Result<String>>()
    val addGroupResult = _addGroupResult as LiveData<Result<String>>

    val usersList = mutableSetOf<String>()

    fun search(query: String) {
        viewModelScope.launch {
            _searchUsersResult.value = searchUsers(query)!!
        }
    }

    fun addGroup(workspaceId: String, name: String) {
        viewModelScope.launch {
            _addGroupResult.value = addGroup(Triple(workspaceId, name, usersList))!!
        }
    }
}