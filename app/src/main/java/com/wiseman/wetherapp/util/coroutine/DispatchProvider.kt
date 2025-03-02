package com.wiseman.wetherapp.util.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfine: CoroutineDispatcher
}