package com.jeevan.ui.createTaskDirect

import androidx.lifecycle.*
import com.jeevan.domain.task.CreateDirectTaskUseCase
import com.jeevan.fragment.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createDirectTask: CreateDirectTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val directId: String by lazy {
        savedStateHandle["direct_id"]!!
    }
    var deadlineDate: Date? = null
    private val _task = MutableLiveData<Result<Task>>()
    val task = _task as LiveData<Result<Task>>

    fun createTask(text: String) {
        viewModelScope.launch {
            _task.value = createDirectTask(Triple(directId, text, deadlineDate))!!
        }
    }

}