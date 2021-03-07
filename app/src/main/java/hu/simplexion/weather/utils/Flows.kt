package hu.simplexion.weather.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow

fun interval(delayMillis: Long, initialDelayMillis: Long = 0L) = flow {
    require(delayMillis > 0) { "delayMillis must be positive" }
    require(initialDelayMillis >= 0) { "initialDelayMillis cannot be negative" }

    if (initialDelayMillis > 0) {
        delay(initialDelayMillis)
    }
    emit(System.currentTimeMillis())
    while (true) {
        delay(delayMillis)
        emit(System.currentTimeMillis())
    }
}.cancellable().buffer()