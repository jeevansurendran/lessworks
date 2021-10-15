package com.jeevan.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeevan.data.token.model.TokenState
import com.jeevan.domain.token.AddUserToWorkspaceCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewWorkspaceUserViewModel @Inject constructor(private val addUserToWorkspace: AddUserToWorkspaceCase) :
    ViewModel() {
    private val _result = MutableLiveData<Result<TokenState>>()
    val result = _result

    fun addUserWithToken(tokenId: String) {
        viewModelScope.launch {
            _result.value = addUserToWorkspace(tokenId)
        }
    }
}