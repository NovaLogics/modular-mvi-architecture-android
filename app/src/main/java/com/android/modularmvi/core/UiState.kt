package com.android.modularmvi.core

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val data: Any) : UiState()
    data class Error(val message: String) : UiState()
}
