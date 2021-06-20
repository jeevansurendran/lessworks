package com.jeevan.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jeevan.domain.auth.GetAuthUserFlowCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getAuthUserFlowCase: GetAuthUserFlowCase) :
    ViewModel() {
    val isSignedIn by lazy {
        getAuthUserFlowCase(Unit).asLiveData()
    }
}