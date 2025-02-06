package com.android.modularmvi.domain.repository

interface HomeRepository {
    suspend fun getItems(): List<String>
}
