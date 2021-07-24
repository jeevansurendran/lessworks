package com.jeevan.ui.group

import androidx.lifecycle.*
import com.jeevan.domain.workspace.GetGroupUseCase
import com.jeevan.queries.GetGroupQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getGroup: GetGroupUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _group = MutableLiveData<Result<GetGroupQuery.Group>>()
    val group = _group as LiveData<Result<GetGroupQuery.Group>>
    val groupId: String by lazy {
        savedStateHandle["group_id"]!!
    }

    private fun getGroupData() {
        viewModelScope.launch {
            _group.value = getGroup(groupId)!!
        }
    }

    init {
        getGroupData()
    }

}