package com.android.modularmvi.domain.usecase

import com.android.modularmvi.domain.model.Quote
import com.android.modularmvi.domain.repository.LocalDataRepository
import com.android.modularmvi.domain.repository.QuoteRepository
import com.android.modularmvi.util.QUOTE_LIMIT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetQuotesFromDbUseCase @Inject constructor(
    private val localDataRepository: LocalDataRepository,
    private val quoteRepository: QuoteRepository,
) {
    suspend fun execute(quoteLimit: Int): Flow<List<Quote>> {
        val isDatabaseLow = quoteRepository.getQuoteCount() < QUOTE_LIMIT
         if (isDatabaseLow) {
             val offlineQuotes = localDataRepository.getOfflineQuotes().first() // Get the first emission
             if (offlineQuotes.isNotEmpty()) {
                 quoteRepository.insertQuotes(offlineQuotes)
             }
        }
        return quoteRepository.getLastNQuotes(limit = quoteLimit)
    }
}
