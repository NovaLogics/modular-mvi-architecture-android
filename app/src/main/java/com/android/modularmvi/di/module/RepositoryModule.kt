package com.android.modularmvi.di.module

import com.android.modularmvi.data.local.datasource.HomeLocalDataSource
import com.android.modularmvi.data.remote.ApiService
import com.android.modularmvi.data.repository.HomeRepositoryImpl
import com.android.modularmvi.domain.repository.HomeRepository
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
    fun provideHomeLocalDataSource(): HomeLocalDataSource {
        return HomeLocalDataSource()
    }

    @Provides
    @Singleton
    fun provideRepository(
        apiService: ApiService,
        homeLocalDataSource: HomeLocalDataSource
    ): HomeRepository {
        return HomeRepositoryImpl(
            apiService = apiService,
            localDataSource = homeLocalDataSource
        )
    }
}
