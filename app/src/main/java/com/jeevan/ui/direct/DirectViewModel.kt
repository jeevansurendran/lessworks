package com.jeevan.ui.direct

import androidx.lifecycle.*
import com.jeevan.domain.auth.GetUserFlowCase
import com.jeevan.domain.task.UpdateTaskUseCase
import com.jeevan.domain.workspace.GetDirectUseCase
import com.jeevan.fragment.Direct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectViewModel @Inject constructor(
    private val getDirect: GetDirectUseCase,
    private val getUserFlow: GetUserFlowCase,
    private val updateTask: UpdateTaskUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _direct = MutableLiveData<Result<Direct>>()
    val direct = _direct as  LiveData<Result<Direct>>
    private val userId: String by lazy {
        savedStateHandle["user_id"]!!
    }
    private val workspaceId: String by lazy {
        savedStateHandle["workspace_id"]!!
    }
    val user by lazy {
        getUserFlow(Unit)
    }

    fun getDirect() {
        viewModelScope.launch {
            _direct.value = getDirect(workspaceId to userId)!!
        }
    }

    fun updateDirectTask(taskId: String, status: Boolean) {
        viewModelScope.launch {
            updateTask(taskId to status)
            getDirect()
        }
    }

    init {
        getDirect()
    }
}
