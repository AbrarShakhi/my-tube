package com.github.abrarshakhi.mytube.presentation.state

sealed interface AddChannelWithFilterState {
    object Loading : AddChannelWithFilterState
    object Cleared : AddChannelWithFilterState
    data class Success(val channelName: String) : AddChannelWithFilterState
    data class Error(val message: String) : AddChannelWithFilterState
}