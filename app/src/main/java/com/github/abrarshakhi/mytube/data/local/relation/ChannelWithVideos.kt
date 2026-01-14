package com.github.abrarshakhi.mytube.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.local.entity.VideoEntity

data class ChannelWithVideos(
    @Embedded
    val channel: ChannelEntity,

    @Relation(
        parentColumn = "channelId",
        entityColumn = "channelOwnerId"
    )
    val videos: List<VideoEntity>
)
