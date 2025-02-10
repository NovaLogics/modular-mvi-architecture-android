package com.android.modularmvi.core

/** Represents the state of the UI */
sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val data: Any) : UiState()
    data class Error(val message: String) : UiState()
}
