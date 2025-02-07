package com.android.modularmvi.data.local.datastore

interface DataStoreRepository {

    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String, defaultValue: String): String

    suspend fun putInt(key: String, value: Int)
    suspend fun getInt(key: String, defaultValue: Int): Int

    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean

    suspend fun putFloat(key: String, value: Float)
    suspend fun getFloat(key: String, defaultValue: Float): Float
}
