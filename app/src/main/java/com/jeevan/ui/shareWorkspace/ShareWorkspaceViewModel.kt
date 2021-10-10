package com.jeevan.ui.shareWorkspace

import androidx.lifecycle.*
import com.jeevan.domain.share.ShareWorkspaceUseCase
import com.jeevan.fragment.Workspace_token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareWorkspaceViewModel @Inject constructor(
    shareWorkspaceUseCase: ShareWorkspaceUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val workspaceId: String by lazy {
        savedStateHandle["workspace_id"]!!
    }

    private val _shareMessage = MutableLiveData<Result<Pair<String, Workspace_token>>>()
    val shareMessage = _shareMessage as LiveData<Result<Pair<String, Workspace_token>>>

    init {
        viewModelScope.launch {
            _shareMessage.value = shareWorkspaceUseCase(workspaceId)
        }
    }
}