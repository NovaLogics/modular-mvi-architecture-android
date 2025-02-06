package com.android.modularmvi.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.modularmvi.domain.usecase.GetHomeItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeItemsUseCase: GetHomeItemsUseCase
) : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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

    // Reducer: Updates the state based on the current state and new data
    private fun reduceState(newState: HomeUiState) {
        _uiState.value = newState
    }

    // Load Items
    private fun loadItems() {
        viewModelScope.launch {
            reduceState(_uiState.value.copy(isLoading = true))
            try {
                val items = getHomeItemsUseCase()
                reduceState(_uiState.value.copy(isLoading = false, items = items))
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An unknown error occurred"
                reduceState(_uiState.value.copy(isLoading = false, error = errorMessage))
                _eventFlow.emit(HomeEvent.ShowMessage(errorMessage))
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


