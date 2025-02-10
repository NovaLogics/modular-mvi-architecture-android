package com.android.modularmvi.di.module

import android.content.Context
import com.android.modularmvi.data.local.database.AppDatabase
import com.android.modularmvi.data.local.database.dao.QuoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context = context)
    }

    @Provides
    fun provideQuoteDao(database: AppDatabase): QuoteDao {
        return database.quoteDao()
    }
}
