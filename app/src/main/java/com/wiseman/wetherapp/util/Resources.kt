package com.wiseman.wetherapp.util

sealed class Resources<T>(
    val data: T? = null, val message: String? = null
) {
    class Success<T>(data: T?, message: String? = null) :
        Resources<T>(data = data, message = message)

    class Error<T>(data: T? = null, message: String? = null) :
        Resources<T>(message = message, data = data)
}
