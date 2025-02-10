package com.android.modularmvi.data.remote

import com.android.modularmvi.data.remote.ApiConstants.RANDOM_QUOTES_ENDPOINT
import com.android.modularmvi.domain.model.Quote
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApiService {

    @GET(RANDOM_QUOTES_ENDPOINT)
    suspend fun getQuotes(
        @Query("limit") limit: Int
    ): List<Quote>

    @GET(RANDOM_QUOTES_ENDPOINT)
    suspend fun getRandomQuotes(
        @Query("limit") limit: Int,
        @Query("maxLength") maxLength: Int,
        @Query("minLength") minLength: Int,
        @Query("tags") tags: String,
    ): List<Quote>
}
