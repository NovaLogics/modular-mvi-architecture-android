package com.android.modularmvi.domain.usecase

import com.android.modularmvi.domain.model.HomeItem
import com.android.modularmvi.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeItemsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): Flow<List<HomeItem>> {
        return repository.getLocalItems()
    }
}
