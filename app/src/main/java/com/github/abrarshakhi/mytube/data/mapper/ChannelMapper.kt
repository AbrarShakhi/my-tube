package com.github.abrarshakhi.mytube.data.mapper

import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.domain.model.Channel

fun ChannelEntity.toDomain(): Channel {
    return Channel(
        channelId = channelId,
        name = name,
        hasNewUpload = hasNewUpload,
        lastUploaded = lastUploaded
    )
}

fun Channel.toEntity(): ChannelEntity {
    return ChannelEntity(
        channelId = channelId,
        name = name,
        hasNewUpload = hasNewUpload,
        lastUploaded = lastUploaded
    )
}
