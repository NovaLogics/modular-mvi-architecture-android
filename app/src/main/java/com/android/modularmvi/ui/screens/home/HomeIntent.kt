package com.android.modularmvi.ui.screens.home

/**
 * Represents user actions that the ViewModel can handle.
 */
sealed class HomeIntent {
    object LoadData : HomeIntent()
    data class OnItemClick(val itemId: String) : HomeIntent()
}

