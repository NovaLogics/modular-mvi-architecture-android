package com.android.modularmvi.domain.repository

import com.android.modularmvi.core.Response
import com.android.modularmvi.domain.model.HomeItem
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getLocalItems(): Flow<List<HomeItem>>
    suspend fun getRemoteItems(): Response<List<String>>
}
