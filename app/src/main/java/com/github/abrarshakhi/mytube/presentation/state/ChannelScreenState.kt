package com.github.abrarshakhi.mytube.presentation.state

sealed interface ChannelScreenState{
    object Sheet: ChannelScreenState
    object Dialog: ChannelScreenState
    object Hidden: ChannelScreenState
}
