package com.example.marvelchallenge.presentation.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

interface EventEmitter<E> {

    val uiEvent: Flow<E>

    suspend fun sendEvent(event: E)
}

class EventEmitterImpl<E> : EventEmitter<E> {

    private val _uiEvent = Channel<E>(Channel.RENDEZVOUS)
    override val uiEvent: Flow<E> = _uiEvent.receiveAsFlow()

    override suspend fun sendEvent(event: E) = withContext(Dispatchers.Main.immediate) {
        _uiEvent.send(event)
    }
}
