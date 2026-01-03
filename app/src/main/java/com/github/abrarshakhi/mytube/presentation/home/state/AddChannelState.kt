package com.github.abrarshakhi.mytube.presentation.home.state

import com.github.abrarshakhi.mytube.domain.model.Channel

sealed interface AddChannelState {
    object Cleared : AddChannelState
    object Loading : AddChannelState
    data class Found(val channel: Channel) : AddChannelState
    data class Error(val message: String) : AddChannelState
}