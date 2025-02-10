package com.android.modularmvi.domain.repository

import com.android.modularmvi.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface LocalDataRepository {
    suspend fun getOfflineQuotes(): Flow<List<Quote>>
}
