package com.jeevan.ui.shareWorkspace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeevan.domain.share.ShareWorkspaceUseCase
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

    private val _shareMessage = MutableLiveData<Result<String>>()
    val shareMessage = _shareMessage

    init {
        viewModelScope.launch {
            _shareMessage.value = shareWorkspaceUseCase(workspaceId)
        }
    }
}