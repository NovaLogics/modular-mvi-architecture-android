package com.android.modularmvi.domain.repository

import com.android.modularmvi.core.Response
import com.android.modularmvi.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun fetchQuotesFromApi(limit: Int): Response<List<Quote>>
    suspend fun insertQuotes(quotes: List<Quote>?)
    suspend fun clearQuotes()
    suspend fun getQuoteById(id: String): Quote?
    suspend fun getQuoteCount(): Int
    fun getAllQuotes(): Flow<List<Quote>>
    fun getLastNQuotes(limit: Int): Flow<List<Quote>>
}
