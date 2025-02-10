package com.android.modularmvi.di.module

import com.android.modularmvi.data.local.database.dao.QuoteDao
import com.android.modularmvi.data.local.datasource.LocalDataSource
import com.android.modularmvi.data.remote.QuoteApiService
import com.android.modularmvi.data.repository.LocalDataRepositoryImpl
import com.android.modularmvi.data.repository.QuoteRepositoryImpl
import com.android.modularmvi.domain.repository.LocalDataRepository
import com.android.modularmvi.domain.repository.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideQuoteRepository(
        apiService: QuoteApiService,
        quoteDao: QuoteDao
    ): QuoteRepository {
        return QuoteRepositoryImpl(
            apiService = apiService,
            quoteDao= quoteDao,
        )
    }

    @Provides
    @Singleton
    fun provideLocalDataRepository(
        localDataSource : LocalDataSource,
    ): LocalDataRepository {
        return LocalDataRepositoryImpl(
            localDataSource = localDataSource,
        )
    }
}
