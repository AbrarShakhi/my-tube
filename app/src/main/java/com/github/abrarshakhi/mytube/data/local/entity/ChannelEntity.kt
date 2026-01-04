package com.github.abrarshakhi.mytube.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "channels",
    indices = [Index(value = ["channelId"], unique = true)]
)
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val channelId: String,
    val title: String,
    val thumbnail: ByteArray? = null,
    val hasNewUpload: Boolean = false,
    val lastUploadedAt: Long? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChannelEntity

        if (id != other.id) return false
        if (hasNewUpload != other.hasNewUpload) return false
        if (lastUploadedAt != other.lastUploadedAt) return false
        if (channelId != other.channelId) return false
        if (title != other.title) return false
        if (!thumbnail.contentEquals(other.thumbnail)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + hasNewUpload.hashCode()
        result = 31 * result + (lastUploadedAt?.hashCode() ?: 0)
        result = 31 * result + channelId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (thumbnail?.contentHashCode() ?: 0)
        return result
    }
}
