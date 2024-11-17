package com.wiseman.wetherapp.util

sealed class Resources<T>(
    val data: T? = null, val message: String? = null
) {
    class Success<T>(data: T?, message: String? = null) : Resources<T>(data = data)
    class Error<T>(data: T? = null, message: String?) : Resources<T>(message = message)
}
