package com.example.marvelchallenge.presentation.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.reflect.KProperty1

interface StateHolder<S> {

    val uiState: StateFlow<S>

    fun setState(update: (old: S) -> S)
}

class StateHolderImpl<S>(initialState: S) : StateHolder<S> {

    private val _uiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<S> = _uiState.asStateFlow()

    override fun setState(update: (old: S) -> S) = _uiState.update(update)
}

fun <S, P> StateHolder<S>.property(transform: KProperty1<S, P>): Flow<P> =
    uiState.map(transform).distinctUntilChanged()
