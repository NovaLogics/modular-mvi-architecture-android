package com.android.modularmvi.data.repository

import com.android.modularmvi.core.Response
import com.android.modularmvi.data.local.datasource.HomeLocalDataSource
import com.android.modularmvi.data.remote.ApiService
import com.android.modularmvi.domain.model.HomeItem
import com.android.modularmvi.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val localDataSource : HomeLocalDataSource,
) : HomeRepository {

    override suspend fun getLocalItems(): Flow<List<HomeItem>> {
        return localDataSource.getHomeItems()
    }

    /**
     * Fetches home items from the API and maps them to the domain model.
     */
    override suspend fun getRemoteItems(): Response<List<String>> {
        return try {
            val response = apiService.getHomeItems()
            Response.Success(response)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }
}

