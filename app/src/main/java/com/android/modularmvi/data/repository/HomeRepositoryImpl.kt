package com.android.modularmvi.data.repository

import com.android.modularmvi.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor() : HomeRepository {

    override suspend fun getItems(): List<String> {
        return listOf("Item 1", "Item 2", "Item 3")
    }
}
