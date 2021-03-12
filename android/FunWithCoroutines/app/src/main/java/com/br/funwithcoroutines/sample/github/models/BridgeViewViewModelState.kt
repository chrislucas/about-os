package com.br.funwithcoroutines.sample.github.models

sealed class BridgeViewViewModelState {
    data class OnError(val throwable: Throwable) : BridgeViewViewModelState()
    data class OnSuccess<T>(val value: T): BridgeViewViewModelState()
}
