package com.android.modularmvi.ui.screens.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.modularmvi.domain.model.Quote
import com.android.modularmvi.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _quote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> = _quote.asStateFlow()

    fun loadQuote(quoteId: String) {
        viewModelScope.launch {
            _quote.value = quoteRepository.getQuoteById(quoteId)
        }
    }
}
