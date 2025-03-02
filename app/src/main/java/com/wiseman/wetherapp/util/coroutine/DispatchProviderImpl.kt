package com.wiseman.wetherapp.util.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatchProviderImpl : DispatchProvider {
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val unconfine: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}