package com.github.abrarshakhi.mytube.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "channels_filter",
    foreignKeys = [
        ForeignKey(
            entity = ChannelEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id"], unique = true)]
)
data class ChannelFilterEntity(
    @PrimaryKey
    val id: Long,
    val contains: Boolean,
    val regex: String
)
