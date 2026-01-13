package com.github.abrarshakhi.mytube.domain.repository

interface VideoSyncRepository {
    suspend fun syncVideos() : Result<Unit>
    suspend fun syncVideoForChannel(channelId: String): Result<Unit>
}
