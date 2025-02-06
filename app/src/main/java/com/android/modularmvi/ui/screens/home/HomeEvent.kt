package com.android.modularmvi.ui.screens.home

/**
 * Represents events that the ViewModel emits for UI side effects.
 */
sealed class HomeEvent {
    data class NavigateToItemDetail(val itemId: String) : HomeEvent()
    data class ShowMessage(val message: String) : HomeEvent()
}
