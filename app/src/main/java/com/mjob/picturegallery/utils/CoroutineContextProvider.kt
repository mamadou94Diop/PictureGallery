package com.mjob.picturegallery.utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider {
    open val io : CoroutineContext = Dispatchers.IO
    open val main: CoroutineContext = Dispatchers.Main
}