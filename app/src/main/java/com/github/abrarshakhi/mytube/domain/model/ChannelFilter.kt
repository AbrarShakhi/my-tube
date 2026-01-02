package com.github.abrarshakhi.mytube.domain.model


data class ChannelFilter(
    val contains: Boolean = true,
    val regex: String = ""
)