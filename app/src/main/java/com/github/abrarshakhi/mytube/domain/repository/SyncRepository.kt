package com.github.abrarshakhi.mytube.domain.repository

interface SyncRepository {
    suspend fun syncVideos() : Result<Unit>
}
