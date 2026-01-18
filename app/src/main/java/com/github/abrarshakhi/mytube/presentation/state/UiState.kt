package com.github.abrarshakhi.mytube.presentation.state

data class UiState<T>(
    val content: UiContent<T>,
    val isLoading: Boolean
) {
    fun asLoading() = copy(isLoading = true)
    fun asSuccess(data: T) = success(data)
    fun asError(message: String): UiState<T> = error<T>(message)

    sealed interface UiContent<out T> {
        data class Data<T>(val value: T) : UiContent<T>
        data class Error(val message: String) : UiContent<Nothing>
    }

    companion object {
        fun <T> loading(data: T) = UiState(
            content = UiContent.Data(data),
            isLoading = true
        )

        fun <T> success(data: T) = UiState(
            content = UiContent.Data(data),
            isLoading = false
        )

        fun <T> error(message: String): UiState<T> = UiState(
            content = UiContent.Error(message),
            isLoading = false
        )
    }
}
