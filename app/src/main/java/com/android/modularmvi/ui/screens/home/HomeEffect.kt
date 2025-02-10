package com.android.modularmvi.ui.screens.home

import com.android.modularmvi.core.state.ViewSideEffect

sealed class HomeEffect : ViewSideEffect{
    data class NavigateToItemDetail(val itemId: String) : HomeEffect()
    data class ShowMessage(val message: String) : HomeEffect()
}
