package com.github.abrarshakhi.mytube.presentation.state

import com.github.abrarshakhi.mytube.domain.model.Channel


sealed interface ChannelListState {
    object Loading : ChannelListState
    data class Success(val channels: List<Channel>) : ChannelListState
    data class Error(val message: String) : ChannelListState
}
