package com.github.abrarshakhi.mytube.domain.model

data class Channel(
    val channelId: String,
    val title: String,
    val thumbnail: ByteArray? = null,
    val hasNewUpload: Boolean = false,
    val lastUploadedSince: String,
    val filter: ChannelFilter
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Channel

        if (hasNewUpload != other.hasNewUpload) return false
        if (channelId != other.channelId) return false
        if (title != other.title) return false
        if (!thumbnail.contentEquals(other.thumbnail)) return false
        if (lastUploadedSince != other.lastUploadedSince) return false
        if (filter != other.filter) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hasNewUpload.hashCode()
        result = 31 * result + channelId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (thumbnail?.contentHashCode() ?: 0)
        result = 31 * result + lastUploadedSince.hashCode()
        result = 31 * result + filter.hashCode()
        return result
    }
}