package com.android.modularmvi.domain.usecase

import com.android.modularmvi.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeItemsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getItems()
    }
}
