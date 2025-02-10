package com.android.modularmvi.core

/**
 * Sealed class representing different UI states.
 * @param T The type of data in a successful response.
 */
sealed class Response<out T> {
    data object Loading : Response<Nothing>()
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val exception: Throwable) : Response<Nothing>()
}

/** Safely accesses data from [Response.Success], returns `null` otherwise */
val <T> Response<T>.dataOrNull: T?
    get() = (this as? Response.Success<T>)?.data

/** Checks if the response is a [Response.Success] */
val Response<*>.isSuccessful: Boolean
    get() = this is Response.Success<*>
