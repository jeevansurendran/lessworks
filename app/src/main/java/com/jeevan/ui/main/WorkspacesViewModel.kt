package com.jeevan.ui.main

import androidx.lifecycle.*
import com.jeevan.GetWorkspacesQuery.Workspace
import com.jeevan.domain.workspace.GetWorkspacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkspacesViewModel @Inject constructor(private val getWorkspaces: GetWorkspacesUseCase) :
    ViewModel() {
    private val _workspaces = MutableLiveData<Result<List<Workspace>>?>()
    val workspaces = _workspaces as LiveData<Result<List<Workspace>>?>

    val selectedWorkspace = workspaces.map {
        return@map it?.map { it.takeIf { it.isNotEmpty() }?.first() }
    }

    private fun getWorkspaces() {
        viewModelScope.launch {
            _workspaces.value = getWorkspaces(Unit)
        }
    }


    // needs to be the last, very imp!
    init {
        getWorkspaces()
    }
}