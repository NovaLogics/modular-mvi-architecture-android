package com.android.modularmvi.di.module

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
    fun provideRepository(): HomeRepository {
        return HomeRepositoryImpl()
    }
}
