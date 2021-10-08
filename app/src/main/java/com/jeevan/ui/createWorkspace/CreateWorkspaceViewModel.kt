package com.jeevan.ui.createWorkspace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeevan.domain.workspace.CreateWorkspaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateWorkspaceViewModel @Inject constructor(
    private val createWorkspace: CreateWorkspaceUseCase
) : ViewModel() {
    private val _workspaceId = MutableLiveData<Result<String>>()
    val workspaceId = _workspaceId as LiveData<Result<String>>

    fun createNewWorkspace(name: String) {
        viewModelScope.launch {
            _workspaceId.value = createWorkspace(name)!!
        }
    }

}