package com.github.abrarshakhi.mytube.data.mapper

import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.local.entity.ChannelFilterEntity
import com.github.abrarshakhi.mytube.data.local.relation.ChannelWithFilter
import com.github.abrarshakhi.mytube.data.remote.dto.ChannelDto
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.model.ChannelFilter
import com.github.abrarshakhi.mytube.domain.utils.toTimeSince


fun ChannelEntity.toDomain(): Channel {
    return Channel(
        channelId = channelId,
        title = title,
        thumbnail = thumbnail,
        hasNewUpload = hasNewUpload,
        lastUploadedSince = lastUploadedAt?.toTimeSince() ?: "No New Uploads",
        filter = ChannelFilter()
    )
}

fun Channel.toEntity(): ChannelEntity {
    return ChannelEntity(
        channelId = channelId,
        title = title,
        thumbnail = thumbnail,
        hasNewUpload = hasNewUpload,
    )
}

fun Channel.toRelation(): ChannelWithFilter {
    return ChannelWithFilter(
        channel = toEntity(),
        filter = ChannelFilterEntity(
            id = -1L,
            contains = filter.contains,
            regex = filter.regex
        )
    )
}

fun ChannelWithFilter.toDomain(): Channel {
    val channel = channel.toDomain()
    channel.filter.copy(
        contains = filter.contains,
        regex = filter.regex
    )
    return channel
}

fun ChannelDto.toEntity(thumbnail: ByteArray?): ChannelEntity {
    return ChannelEntity(
        channelId = id,
        title = snippet.title,
        thumbnail = thumbnail
    )
}

fun ChannelDto.toDomain(thumbnail: ByteArray?): Channel {
    return toEntity(thumbnail).toDomain()
}