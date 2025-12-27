package com.github.abrarshakhi.mytube.domain.repository

import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.usecase.result.Outcome

interface ChannelRepository {
    suspend fun getChannels(): Outcome<List<Channel>>
    suspend fun addChannel(channel: Channel): Outcome<String>
    suspend fun findChannel(channelId: String): Outcome<Channel>
}
