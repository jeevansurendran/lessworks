package com.jeevan.ui.group

import androidx.lifecycle.*
import com.jeevan.domain.auth.GetAuthUserFlowCase
import com.jeevan.domain.auth.GetUserFlowCase
import com.jeevan.domain.workspace.GetGroupUseCase
import com.jeevan.queries.GetGroupQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getGroup: GetGroupUseCase,
    private val getUserFlowCase: GetUserFlowCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _group = MutableLiveData<Result<GetGroupQuery.Group>>()
    val group = _group as LiveData<Result<GetGroupQuery.Group>>
    val groupId: String by lazy {
        savedStateHandle["group_id"]!!
    }
    val user by lazy {
        getUserFlowCase(Unit)
    }

    fun getGroupData() {
        viewModelScope.launch {
            _group.value = getGroup(groupId)!!
        }
    }

    init {
        getGroupData()
    }

}