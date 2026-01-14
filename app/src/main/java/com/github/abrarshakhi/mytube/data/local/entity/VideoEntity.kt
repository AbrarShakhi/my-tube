package com.github.abrarshakhi.mytube.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "videos",
    foreignKeys = [
        ForeignKey(
            entity = ChannelEntity::class,
            parentColumns = ["channelId"],
            childColumns = ["channelOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("channelOwnerId"),
        Index(value = ["videoId"], unique = true)
    ]
)
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val videoId: String,
    val channelOwnerId: String,

    val title: String,
    val thumbnailUrl: String?,
    val publishedAt: Long
) {
    val videoUrl: String
        get() = "https://www.youtube.com/watch?v=$videoId"
}