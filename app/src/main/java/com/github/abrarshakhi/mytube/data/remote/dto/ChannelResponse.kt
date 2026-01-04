package com.github.abrarshakhi.mytube.data.remote.dto

data class ChannelResponse(
    val items: List<ChannelDto>
)

data class ChannelDto(
    val id: String,
    val snippet: SnippetDto
)

data class SnippetDto(
    val title: String,
    val thumbnails: ThumbnailsDto
)

data class ThumbnailsDto(
    val default: ThumbnailDto?,
)

data class ThumbnailDto(
    val url: String
)