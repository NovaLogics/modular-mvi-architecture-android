package com.android.modularmvi.core

/**
 * Sealed class representing different UI states.
 */
sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val exception: Throwable) : Response<Nothing>()
    object Loading : Response<Nothing>()
}
