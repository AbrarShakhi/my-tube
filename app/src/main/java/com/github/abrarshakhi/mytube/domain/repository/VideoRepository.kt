package com.github.abrarshakhi.mytube.domain.repository

import com.github.abrarshakhi.mytube.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    fun getVideos(): Flow<List<Video>>
}
