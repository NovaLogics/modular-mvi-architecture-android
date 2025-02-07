package com.android.modularmvi.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.android.modularmvi.core.BaseViewModel
import com.android.modularmvi.domain.usecase.GetHomeItemsUseCase
import com.android.modularmvi.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeItemsUseCase: GetHomeItemsUseCase
) : BaseViewModel<HomeUiState>(HomeUiState()) {

    // Side Effects
    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow: SharedFlow<HomeEvent> = _eventFlow.asSharedFlow()

    init {
        handleIntent(HomeIntent.LoadData)
    }

    // Intent Handler
    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> loadItems()
            is HomeIntent.OnItemClick -> onItemClicked(intent.itemId)
        }
    }

    // Load Items
    private fun loadItems() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            try {
                getHomeItemsUseCase().collectLatest { items ->
                    setState { copy(isLoading = false, items = items) }
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false, error = e.getErrorMessage()) }
                _eventFlow.emit(HomeEvent.ShowMessage(e.getErrorMessage()))
            }
        }
    }

    // Handle Item Click
    private fun onItemClicked(itemId: String) {
        viewModelScope.launch {
            _eventFlow.emit(HomeEvent.ShowMessage(message = "ItemId: $itemId"))
            _eventFlow.emit(HomeEvent.NavigateToItemDetail(itemId))
        }
    }
}
