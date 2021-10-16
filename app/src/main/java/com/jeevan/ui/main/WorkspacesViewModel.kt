package com.jeevan.ui.main

import androidx.lifecycle.*
import com.jeevan.domain.workspace.GetWorkspacesUseCase
import com.jeevan.fragment.Group
import com.jeevan.queries.GetWorkspacesQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkspacesViewModel @Inject constructor(private val getWorkspaces: GetWorkspacesUseCase) :
    ViewModel() {
    private val _workspaces = MutableLiveData<Result<MutableList<GetWorkspacesQuery.Workspace>>?>()
    val workspaces = _workspaces as LiveData<Result<MutableList<GetWorkspacesQuery.Workspace>>?>
    private val _workspaceId = MutableLiveData<String?>()
    val workspaceId = _workspaceId as LiveData<String?>

    val selectedWorkspace = workspaceId.map { workspaceId ->
        return@map workspaces.value?.map {
            it.takeIf { it.isNotEmpty() }?.find { it.id == workspaceId }
        }
    }

    fun getWorkspaces() {
        viewModelScope.launch {
            val fetchedWorkspace = getWorkspaces(Unit).map { it.toMutableList() }
            _workspaceId.value = fetchedWorkspace.getOrNull()?.first()?.id as String
            _workspaces.value = fetchedWorkspace
            _workspaceId.value = fetchedWorkspace.getOrNull()?.first()?.id as String
        }
    }

     fun setWorkspaceId(workspaceId: String) {
        _workspaceId.value = workspaceId
    }

    fun addGroup(workspaceId: String, group: Group) {
        workspaces.value?.getOrNull()?.let { list ->
            list.find { it.id == workspaceId }?.let {
                val index = list.indexOf(it)
                it.let {
                    val newGroup = it.groups
                        .toMutableList()
                        .apply {
                            add(
                                GetWorkspacesQuery.Group(
                                    fragments = GetWorkspacesQuery.Group.Fragments(
                                        group
                                    )
                                )
                            )
                        }

                    list[index] = GetWorkspacesQuery.Workspace(
                        id = it.id,
                        groups = newGroup,
                        name = it.name,
                        workspace_users = it.workspace_users
                    )
                    _workspaces.value = _workspaces.value
                }
            }
        }
    }


    // needs to be the last, very imp!
    init {
        getWorkspaces()
    }
}