package com.android.modularmvi.ui.screens.home

import com.android.modularmvi.domain.model.HomeItem
import com.android.modularmvi.util.emptyString

data class HomeUiState(
    val isLoading: Boolean = false,
    val items: List<HomeItem> = emptyList(),
    val error: String = emptyString(),
)

