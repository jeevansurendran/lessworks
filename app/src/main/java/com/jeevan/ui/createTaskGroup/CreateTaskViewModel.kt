package com.jeevan.ui.createTaskGroup

import androidx.lifecycle.*
import com.jeevan.domain.task.CreateGroupTaskUseCase
import com.jeevan.fragment.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createGroupTask: CreateGroupTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val groupId: String by lazy {
        savedStateHandle["group_id"]!!
    }
    var deadlineDate: Date? = null
    private val _task = MutableLiveData<Result<Task>>()
    val task = _task as LiveData<Result<Task>>

    fun createTask(text: String) {
        viewModelScope.launch {
            _task.value = createGroupTask(Triple(groupId, text, deadlineDate))!!
        }
    }

}