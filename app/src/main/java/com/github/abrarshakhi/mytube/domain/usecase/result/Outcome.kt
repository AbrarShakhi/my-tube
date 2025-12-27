package com.github.abrarshakhi.mytube.domain.usecase.result

sealed interface Outcome<out V> {
    data class Success<V>(val value: V): Outcome<V>
    data class Failure<V>(val error: Error): Outcome<V>
}