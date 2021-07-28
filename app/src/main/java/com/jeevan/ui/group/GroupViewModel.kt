package com.jeevan.ui.group

import androidx.lifecycle.*
import com.jeevan.domain.auth.GetUserFlowCase
import com.jeevan.domain.task.UpdateTaskUseCase
import com.jeevan.domain.workspace.GetGroupUseCase
import com.jeevan.queries.GetGroupQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getGroup: GetGroupUseCase,
    private val getUserFlow: GetUserFlowCase,
    private val updateTask: UpdateTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _group = MutableLiveData<Result<GetGroupQuery.Group>>()
    val group = _group as LiveData<Result<GetGroupQuery.Group>>
    val groupId: String by lazy {
        savedStateHandle["group_id"]!!
    }
    val user by lazy {
        getUserFlow(Unit)
    }

    fun getGroupData() {
        viewModelScope.launch {
            _group.value = getGroup(groupId)!!
        }
    }

    fun updateGroupTask(taskId: String, status: Boolean) {
        viewModelScope.launch {
            updateTask(taskId to status)
            getGroupData()
        }
    }

    init {
        getGroupData()
    }

}