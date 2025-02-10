package com.android.modularmvi.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.modularmvi.core.state.ViewIntent
import com.android.modularmvi.core.state.ViewSideEffect
import com.android.modularmvi.core.state.ViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel for managing intents, UI state, and side effects
 *
 * @param Intent Type of user actions/intents
 * @param UiState Type of the UI state
 * @param Effect Type of side effects
 * @param initialState Initial UI state
 */
abstract class BaseViewModel<
        Intent : ViewIntent,
        UiState : ViewState,
        Effect : ViewSideEffect
        >
    (initialState: UiState) : ViewModel() {

    /** Handles incoming intents */
    abstract fun handleIntent(intent: Intent)

    /** Internal mutable state */
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    /** Exposed immutable state for UI observation */
    val uiState: StateFlow<UiState> = _uiState

    /** Channel for emitting side effects */
    private val _uiEffect = Channel<Effect>(Channel.BUFFERED)
    /** Flow for observing side effects */
    val uiEffect = _uiEffect.receiveAsFlow()

    /** Buffered flow for intents to prevent loss */
    private val _intentFlow = MutableSharedFlow<Intent>(extraBufferCapacity = 64)

    init {
        subscribeToIntents()
    }

    /** Collects and processes intents */
    private fun subscribeToIntents() {
        viewModelScope.launch {
            _intentFlow.collect { intent ->
                handleIntent(intent)
            }
        }
    }

    /** Sends an intent for processing */
    fun sendIntent(intent: Intent) {
        viewModelScope.launch { _intentFlow.emit(intent) }
    }

    /** Updates the current UI state using a reducer function */
    protected fun updateState(reducer: UiState.() -> UiState) {
        _uiState.value = _uiState.value.reducer()
    }

    /** Sends a side effect to be processed */
    protected fun sendEffect(builder: suspend () -> Effect) {
        viewModelScope.launch {
            _uiEffect.send(builder())
        }
    }
}

