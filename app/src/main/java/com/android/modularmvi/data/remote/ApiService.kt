package com.android.modularmvi.data.remote

import retrofit2.http.GET

/**
 * Defines API endpoints using Retrofit.
 */
interface ApiService {
    @GET("home/items")
    suspend fun getHomeItems(): List<String>
}
