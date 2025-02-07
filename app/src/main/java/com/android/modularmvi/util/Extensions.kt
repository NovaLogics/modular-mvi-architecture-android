package com.android.modularmvi.util

/**
 * Extension function to get a user-friendly error message from a Throwable
 */
fun Throwable.getErrorMessage(): String {
    return this.localizedMessage ?: "An unknown error occurred"
}
