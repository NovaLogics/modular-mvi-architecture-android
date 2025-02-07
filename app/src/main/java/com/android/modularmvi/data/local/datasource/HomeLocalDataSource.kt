package com.android.modularmvi.data.local.datasource

import com.android.modularmvi.domain.model.HomeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeLocalDataSource {

    // Hardcoded data (Mocked local data)
    private val homeItems = listOf(
        HomeItem(id = 1, title = "Item 1", description = "This is item 1"),
        HomeItem(id = 2, title = "Item 2", description = "This is item 2"),
        HomeItem(id = 3, title = "Item 3", description = "This is item 3")
    )

    // Exposing as Flow
    fun getHomeItems(): Flow<List<HomeItem>> = flow {
        emit(homeItems)
    }
}
