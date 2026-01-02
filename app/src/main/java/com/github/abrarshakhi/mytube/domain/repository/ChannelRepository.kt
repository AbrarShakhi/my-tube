package com.github.abrarshakhi.mytube.domain.repository

import com.github.abrarshakhi.mytube.domain.model.Channel

interface ChannelRepository {
    suspend fun getChannels(): Result<List<Channel>>
    suspend fun addChannel(handle: String): Result<String>
    suspend fun findChannel(handle: String): Result<Channel>
}
