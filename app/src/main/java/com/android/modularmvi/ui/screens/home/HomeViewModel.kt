package com.android.modularmvi.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.android.modularmvi.core.BaseViewModel
import com.android.modularmvi.core.Response
import com.android.modularmvi.domain.usecase.FetchQuotesFromApiUseCase
import com.android.modularmvi.domain.usecase.GetQuotesFromDbUseCase
import com.android.modularmvi.util.QUOTE_LIMIT
import com.android.modularmvi.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchQuotesFromApiUseCase: FetchQuotesFromApiUseCase,
    private val getQuotesFromDbUseCase: GetQuotesFromDbUseCase
) : BaseViewModel<HomeIntent, HomeUiState, HomeEffect>(HomeUiState()) {

    init {
        handleIntent(HomeIntent.LoadOfflineQuotes)
    }

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadOfflineQuotes -> loadOfflineQuotes()
            is HomeIntent.FetchLiveQuotes -> fetchLiveQuotes()
            is HomeIntent.OnItemClick -> handleItemClick(intent.itemId)
        }
    }

    /**
     * Loads quotes from the local database.
     */
    private fun loadOfflineQuotes() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                getQuotesFromDbUseCase.execute(QUOTE_LIMIT).collect { quotes ->
                    updateState { copy(isLoading = false, quotes = quotes) }
                }
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    /**
     * Fetches quotes from the API.
     */
    private fun fetchLiveQuotes() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            try {
                when (val response = fetchQuotesFromApiUseCase.invoke(QUOTE_LIMIT)) {
                    is Response.Success -> {
                        updateState { copy(isLoading = false, quotes = response.data) }
                    }
                    is Response.Error -> {
                        updateState { copy(isLoading = false) }
                        sendEffect { HomeEffect.ShowMessage(response.exception.getErrorMessage()) }
                    }
                    Response.Loading -> Unit
                }
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    /**
     * Handles item click events.
     * @param itemId The ID of the clicked item.
     */
    private fun handleItemClick(itemId: String) {
        viewModelScope.launch {
            sendEffect { HomeEffect.ShowMessage("Quote Id: $itemId") }
            sendEffect { HomeEffect.NavigateToItemDetail(itemId) }
        }
    }

    /**
     * Handles exceptions by updating UI state and sending effects.
     * @param exception The thrown exception.
     */
    private fun handleException(exception: Exception) {
        val errorMessage = exception.getErrorMessage()
        updateState { copy(isLoading = false, error = errorMessage) }
        sendEffect { HomeEffect.ShowMessage(errorMessage) }
    }
}
