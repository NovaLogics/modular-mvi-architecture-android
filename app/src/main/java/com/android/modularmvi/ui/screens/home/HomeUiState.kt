package com.android.modularmvi.ui.screens.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val items: List<String> = emptyList(),
    val error: String = ""
)

