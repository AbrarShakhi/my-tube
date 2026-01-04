package com.github.abrarshakhi.mytube.presentation.home.state


sealed interface RemoveChannelState {
    object Loading : RemoveChannelState
    object Success : RemoveChannelState
    data class Error(val message: String) : RemoveChannelState
}