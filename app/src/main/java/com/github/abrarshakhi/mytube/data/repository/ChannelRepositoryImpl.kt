package com.github.abrarshakhi.mytube.data.repository

import com.github.abrarshakhi.mytube.BuildConfig
import com.github.abrarshakhi.mytube.data.local.dao.ChannelDao
import com.github.abrarshakhi.mytube.data.local.dao.ChannelFilterDao
import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.mapper.toDomain
import com.github.abrarshakhi.mytube.data.mapper.toEntity
import com.github.abrarshakhi.mytube.data.remote.api.ChannelApi
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import com.github.abrarshakhi.mytube.domain.usecase.result.Error
import com.github.abrarshakhi.mytube.domain.usecase.result.Outcome
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChannelRepositoryImpl @Inject constructor(
    private val channelDao: ChannelDao,
    private val channelFilterDao: ChannelFilterDao,
    private val channelApi: ChannelApi
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

    override suspend fun addChannel(handle: String): Outcome<String> {
        val channelEntityOutcome = findChannel(handle)
        if (channelEntityOutcome is Outcome.Failure) {
            return Outcome.Failure(channelEntityOutcome.error)
        }
        val channelEntity = (channelEntityOutcome as Outcome.Success).value
        return try {
            withContext(Dispatchers.IO) {
                channelDao.insertChannel(channelEntity)
            }
            Outcome.Success(channelEntity.name)
        } catch (e: Exception) {
            Outcome.Failure(Error.UnknownError(e.message ?: "Unknown error"))
        }
    }

    private suspend fun findChannel(handle: String): Outcome<ChannelEntity> {
        return try {
            val response = withContext(Dispatchers.IO) {
                channelApi.getChannelById(
                    handle = handle,
                    apiKey = BuildConfig.YOUTUBE_API_KEY
                )
            }

            val channelDto = response.items.firstOrNull() ?: return Outcome.Failure(
                Error.NotFoundError("No Channel Found")
            )

            Outcome.Success(channelDto.toEntity())

        } catch (_: SocketTimeoutException) {
            Outcome.Failure(Error.NetworkError("Connection timed out. Please try again."))
        } catch (_: UnknownHostException) {
            Outcome.Failure(Error.NetworkError("No internet connection"))
        } catch (_: IOException) {
            Outcome.Failure(Error.NetworkError("Network error occurred"))
        } catch (e: HttpException) {
            Outcome.Failure(Error.ServerError("Server error: ${e.code()}"))
        } catch (e: Exception) {
            Outcome.Failure(Error.UnknownError(e.message ?: "Unknown error"))
        }
    }


}
