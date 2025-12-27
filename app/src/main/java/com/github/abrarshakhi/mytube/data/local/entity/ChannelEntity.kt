package com.github.abrarshakhi.mytube.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "channels",
    indices = [
        Index(value = ["channelId"], unique = true)
    ]
)
data class ChannelEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val channelId: String,

    val name: String,
    val hasNewUpload: Boolean,
    val lastUploaded: String
)
