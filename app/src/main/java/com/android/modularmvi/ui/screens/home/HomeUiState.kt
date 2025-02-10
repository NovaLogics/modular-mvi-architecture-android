package com.android.modularmvi.ui.screens.home

import com.android.modularmvi.core.state.ViewState
import com.android.modularmvi.domain.model.Quote
import com.android.modularmvi.util.emptyString

data class HomeUiState(
    val isLoading: Boolean = false,
    val quotes: List<Quote> = emptyList(),
    val error: String = emptyString(),
) : ViewState

