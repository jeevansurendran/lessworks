package com.jeevan.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.jeevan.GetWorkspacesQuery.Workspace
import com.jeevan.domain.workspace.GetWorkspacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkspacesViewModel @Inject constructor(private val getWorkspaces: GetWorkspacesUseCase) :
    ViewModel() {
    val workspaces = MutableLiveData<Result<List<Workspace>>?>()

    val selectedWorkspace = workspaces.map {
        return@map it?.map { it.takeIf { it.isNotEmpty() }?.first() }
    }

    fun getWorkspaces() {
        viewModelScope.launch {
            workspaces.value = getWorkspaces(Unit)
        }
    }

}