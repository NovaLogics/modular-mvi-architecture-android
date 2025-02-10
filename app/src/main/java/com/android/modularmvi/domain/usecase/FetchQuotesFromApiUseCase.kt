package com.android.modularmvi.domain.usecase

import com.android.modularmvi.core.Response
import com.android.modularmvi.core.dataOrNull
import com.android.modularmvi.domain.model.Quote
import com.android.modularmvi.domain.repository.QuoteRepository
import javax.inject.Inject

class FetchQuotesFromApiUseCase @Inject constructor(
    private val quoteRepository: QuoteRepository
) {
    suspend operator fun invoke(quoteLimit: Int): Response<List<Quote>> {
        val response = quoteRepository.fetchQuotesFromApi(limit = quoteLimit)
        quoteRepository.insertQuotes(response.dataOrNull)
        return response
    }
}
