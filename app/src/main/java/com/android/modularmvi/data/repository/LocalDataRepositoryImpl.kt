package com.android.modularmvi.data.repository

import com.android.modularmvi.data.local.datasource.LocalDataSource
import com.android.modularmvi.domain.model.Quote
import com.android.modularmvi.domain.repository.LocalDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataRepositoryImpl @Inject constructor(
    private val localDataSource : LocalDataSource,
) : LocalDataRepository {

    override suspend fun getOfflineQuotes(): Flow<List<Quote>> {
        return localDataSource.getOfflineQuotes()
    }
}

