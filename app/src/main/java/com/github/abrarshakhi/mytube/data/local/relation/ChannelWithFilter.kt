package com.github.abrarshakhi.mytube.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.local.entity.ChannelFilterEntity

data class ChannelWithFilter(
    @Embedded
    val channel: ChannelEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val filter: ChannelFilterEntity?
)
