package com.github.abrarshakhi.mytube.domain.usecase.result

sealed class Error {
    data class NotImplementedError(val message: String) : Error()
    data class NetworkError(val message: String) : Error()
    data class NotFoundError(val message: String) : Error()
    data class UnknownError(val message: String) : Error()

    fun message(): String {
        return when (this) {
            is NetworkError -> this.message
            is NotFoundError -> this.message
            is NotImplementedError -> this.message
            is UnknownError -> this.message
        }
    }
}