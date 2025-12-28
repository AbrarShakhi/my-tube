package com.github.abrarshakhi.mytube.data.mapper

import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.remote.dto.ChannelDto
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.utils.toTimeSince


fun ChannelEntity.toDomain(): Channel {

    return Channel(
        channelId = channelId,
        name = name,
        hasNewUpload = hasNewUpload,
        lastUploadedSince = lastUploadedAt?.toTimeSince() ?: "No New Uploads"
    )
}

fun Channel.toEntity(): ChannelEntity {
    return ChannelEntity(
        channelId = channelId,
        name = name,
        hasNewUpload = hasNewUpload,
        lastUploadedAt = null
    )
}

fun ChannelDto.toDomain(): Channel {
    return Channel(
        channelId = id,
        name = snippet.title,
        hasNewUpload = false,
        lastUploadedSince = "No New Uploads"
    )
}

fun ChannelDto.toEntity(): ChannelEntity {
    return ChannelEntity(
        channelId = id,
        name = snippet.title,
        hasNewUpload = false,
        lastUploadedAt = null
    )
}