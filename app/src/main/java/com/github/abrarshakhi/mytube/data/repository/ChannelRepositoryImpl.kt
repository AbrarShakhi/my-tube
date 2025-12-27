package com.github.abrarshakhi.mytube.data.repository

import com.github.abrarshakhi.mytube.data.local.dao.ChannelDao
import com.github.abrarshakhi.mytube.data.mapper.toDomain
import com.github.abrarshakhi.mytube.data.mapper.toEntity
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import com.github.abrarshakhi.mytube.domain.usecase.result.Error
import com.github.abrarshakhi.mytube.domain.usecase.result.Outcome
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChannelRepositoryImpl @Inject constructor(
    private val channelDao: ChannelDao
) : ChannelRepository {

    override suspend fun getChannels(): Outcome<List<Channel>> {
        return try {
            val entities = withContext(Dispatchers.IO) {
                channelDao.getChannels()
            }
            val channels = entities.map { it.toDomain() }
            Outcome.Success(channels)
        } catch (e: Exception) {
            Outcome.Failure(Error.UnknownError(e.message ?: "Unknown error"))
        }
    }

    override suspend fun addChannel(channel: Channel): Outcome<String> {
        return try {
            withContext(Dispatchers.IO) {
                channelDao.insertChannel(channel.toEntity())
            }
            Outcome.Success(channel.channelId)
        } catch (e: Exception) {
            Outcome.Failure(Error.UnknownError(e.message ?: "Unknown error"))
        }
    }

    override suspend fun findChannel(channelId: String): Outcome<Channel> {
        TODO("Not yet implemented")
    }


}
