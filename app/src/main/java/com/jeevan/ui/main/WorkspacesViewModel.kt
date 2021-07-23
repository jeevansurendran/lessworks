package com.jeevan.ui.main

import androidx.lifecycle.*
import com.jeevan.GetWorkspacesQuery
import com.jeevan.GetWorkspacesQuery.Workspace
import com.jeevan.domain.workspace.GetWorkspacesUseCase
import com.jeevan.fragment.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkspacesViewModel @Inject constructor(private val getWorkspaces: GetWorkspacesUseCase) :
    ViewModel() {
    private val _workspaces = MutableLiveData<Result<MutableList<Workspace>>?>()
    val workspaces = _workspaces as LiveData<Result<MutableList<Workspace>>?>

    val selectedWorkspace = workspaces.map {
        return@map it?.map { it.takeIf { it.isNotEmpty() }?.first() }
    }

    private fun getWorkspaces() {
        viewModelScope.launch {
            _workspaces.value = getWorkspaces(Unit).map { it.toMutableList() }
        }
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

                    list[index] = Workspace(
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