package com.mjob.picturegallery.helpers

import com.mjob.picturegallery.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider @Inject constructor() : CoroutineContextProvider() {
    override val io : CoroutineContext = Dispatchers.Unconfined
    override val main: CoroutineContext = Dispatchers.Unconfined
}