package com.android.modularmvi.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<T>(initialState: T) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    /**
     * Updates the UI state using a reducer function
     */
    protected fun setState(reducer: T.() -> T) {
        _uiState.value = _uiState.value.reducer()
    }
}

