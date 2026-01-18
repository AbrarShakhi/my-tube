package com.github.abrarshakhi.mytube.data.local.relation;

import androidx.room.Embedded
import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.local.entity.VideoEntity

data class VideoWithChannel(
    @Embedded(prefix = "v_")
    val video: VideoEntity,

    @Embedded(prefix = "c_")
    val channel: ChannelEntity
)
