package com.github.abrarshakhi.mytube.data.mapper

import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.local.entity.ChannelFilterEntity
import com.github.abrarshakhi.mytube.data.local.entity.VideoEntity
import com.github.abrarshakhi.mytube.data.local.relation.ChannelWithFilter
import com.github.abrarshakhi.mytube.data.local.relation.ChannelWithVideos
import com.github.abrarshakhi.mytube.data.local.relation.VideoWithChannel
import com.github.abrarshakhi.mytube.data.remote.dto.ChannelDto
import com.github.abrarshakhi.mytube.data.remote.dto.YoutubeVideoEntryDto
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.model.ChannelFilter
import com.github.abrarshakhi.mytube.domain.model.Video
import com.github.abrarshakhi.mytube.domain.utils.toTimeSince
import java.time.Instant


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

fun YoutubeVideoEntryDto.toEntity(channelId: String): VideoEntity? {
    val videoId = id
        ?.takeIf { it.startsWith("yt:video:") }
        ?.substringAfter("yt:video:")
        ?.takeIf { it.isNotBlank() }
        ?: return null

    val publishedAt = try {
        published?.let { Instant.parse(it).toEpochMilli() }
    } catch (_: Exception) {
        null
    } ?: return null

    val safeTitle = title?.takeIf { it.isNotBlank() } ?: return null

    return VideoEntity(
        videoId = videoId,
        channelOwnerId = channelId,
        title = safeTitle,
        thumbnailUrl = mediaGroup?.thumbnail?.url,
        publishedAt = publishedAt
    )
}

fun VideoWithChannel.toDomain(thumbnail: ByteArray?): Video {
    return Video(
        videoId = video.videoId,
        title = video.title,
        videoUrl = video.videoUrl,
        thumbnail = thumbnail,
        publishedAt = video.publishedAt.toTimeSince(),
        channel = channel.toDomain()
    )
}

fun ChannelWithVideos.toDomain(): List<Video> {
    val channel = channel.toDomain()
    return videos.map {
        return@map Video(
            videoId = it.videoId,
            title = it.title,
            videoUrl = it.videoUrl,
            thumbnail = null,
            publishedAt = it.publishedAt.toTimeSince(),
            channel = channel
        )
    }
}