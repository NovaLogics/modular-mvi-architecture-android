package com.android.modularmvi.data.repository

import com.android.modularmvi.core.Response
import com.android.modularmvi.data.local.database.dao.QuoteDao
import com.android.modularmvi.data.local.database.entity.QuoteEntity
import com.android.modularmvi.data.remote.QuoteApiService
import com.android.modularmvi.domain.model.Quote
import com.android.modularmvi.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val apiService: QuoteApiService,
    private val quoteDao: QuoteDao,
): QuoteRepository {

    /**
     * Fetches home items from the API and maps them to the domain model.
     */
    override suspend fun fetchQuotesFromApi(limit: Int): Response<List<Quote>> {
        return try {
            val response = apiService.getQuotes(limit)
            Response.Success(response)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun insertQuotes(quotes: List<Quote>?) {
        if(quotes == null) return
        quoteDao.insertQuotes(quotes.map { QuoteEntity.fromDomainModel(it) })
    }

    override suspend fun clearQuotes() {
        quoteDao.clearQuotes()
    }

    override suspend fun getQuoteById(id: String): Quote? {
        return quoteDao.getQuoteById(quoteId = id)?.toDomainModel()
    }

    override fun getAllQuotes(): Flow<List<Quote>> {
        return quoteDao.getAllQuotes().map { list -> list.map { it.toDomainModel() } }
    }

    override fun getLastNQuotes(limit: Int): Flow<List<Quote>> {
        return quoteDao.getLastNQuotes(limit).map { list -> list.map { it.toDomainModel() } }
    }

    override suspend fun getQuoteCount(): Int {
        return quoteDao.getQuoteCount()
    }
}
