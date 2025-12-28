package com.github.abrarshakhi.mytube.data.remote.dto

data class ChannelResponse(
    val items: List<ChannelDto>
)

data class ChannelDto(
    val id: String,
    val snippet: Snippet
)

data class Snippet(
    val title: String
)
