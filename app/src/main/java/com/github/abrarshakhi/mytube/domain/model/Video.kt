package com.github.abrarshakhi.mytube.domain.model

data class Video(
    val videoId: String,
    val title: String,
    val videoUrl: String,
    val thumbnail: ByteArray?,
    val publishedAt: String,
    val channel: Channel
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Video

        if (title != other.title) return false
        if (!thumbnail.contentEquals(other.thumbnail)) return false
        if (publishedAt != other.publishedAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + (thumbnail?.contentHashCode() ?: 0)
        result = 31 * result + publishedAt.hashCode()
        return result
    }
}