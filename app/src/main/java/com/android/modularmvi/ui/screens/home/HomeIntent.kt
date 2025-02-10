package com.android.modularmvi.ui.screens.home

import com.android.modularmvi.core.state.ViewIntent

/**
 * Represents user actions that the ViewModel can handle.
 */
sealed class HomeIntent: ViewIntent {
    data object LoadOfflineQuotes : HomeIntent()
    data object FetchLiveQuotes : HomeIntent()
    data class OnItemClick(val itemId: String) : HomeIntent()
}

