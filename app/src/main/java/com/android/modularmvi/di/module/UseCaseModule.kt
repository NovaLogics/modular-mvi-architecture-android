package com.android.modularmvi.di.module

import com.android.modularmvi.data.local.database.dao.QuoteDao
import com.android.modularmvi.data.local.datasource.LocalDataSource
import com.android.modularmvi.data.remote.QuoteApiService
import com.android.modularmvi.data.repository.LocalDataRepositoryImpl
import com.android.modularmvi.data.repository.QuoteRepositoryImpl
import com.android.modularmvi.domain.repository.LocalDataRepository
import com.android.modularmvi.domain.repository.QuoteRepository
import com.android.modularmvi.domain.usecase.FetchQuotesFromApiUseCase
import com.android.modularmvi.domain.usecase.GetQuotesFromDbUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideFetchQuotesFromApiUseCase(
        repository: QuoteRepository
    ): FetchQuotesFromApiUseCase {
        return FetchQuotesFromApiUseCase(
            quoteRepository = repository
        )
    }

    @Provides
    @Singleton
    fun provideGetQuotesFromDbUseCase(
        localDataRepository : LocalDataRepository,
        quoteRepository: QuoteRepository
    ): GetQuotesFromDbUseCase {
        return GetQuotesFromDbUseCase(
            localDataRepository = localDataRepository,
            quoteRepository = quoteRepository
        )
    }
}
