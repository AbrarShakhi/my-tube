package com.github.abrarshakhi.mytube.data.repository

import com.github.abrarshakhi.mytube.BuildConfig
import com.github.abrarshakhi.mytube.data.local.datasource.ChannelWithFilterDataSource
import com.github.abrarshakhi.mytube.data.mapper.toDomain
import com.github.abrarshakhi.mytube.data.mapper.toRelation
import com.github.abrarshakhi.mytube.data.remote.Downloader
import com.github.abrarshakhi.mytube.data.remote.api.YoutubeApi
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import jakarta.inject.Inject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChannelRepositoryImpl @Inject constructor(
    private val channelDataSource: ChannelWithFilterDataSource,
    private val youtubeApi: YoutubeApi
) : ChannelRepository {

    override suspend fun getChannels(): Result<List<Channel>> {
        return Result.success(channelDataSource.getAll().map {
            it.toDomain()
        })
    }

    override suspend fun addChannel(channel: Channel): Result<String> {
        try {
            channelDataSource.insert(channel.toRelation())
            val channelFilter = channelDataSource.get(channel.channelId) ?: return Result.failure(
                Exception("Unable to save channel")
            )
            return Result.success(channelFilter.channel.channelId)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun findChannel(handle: String): Result<Channel> {
        return try {
            val response = youtubeApi.getChannelById(
                handle = handle,
                apiKey = BuildConfig.YOUTUBE_API_KEY
            )

            val channelDto = response.items.firstOrNull()
                ?: return Result.failure(Exception("Channel Not found"))

            val thumbnailBytes: ByteArray? = channelDto.snippet.thumbnails.default?.url?.let { url ->
                Downloader.toBytes(url)
            }

            Result.success(channelDto.toDomain(thumbnailBytes))

        } catch (_: SocketTimeoutException) {
            Result.failure(Exception("Connection timed out. Please try again."))
        } catch (_: UnknownHostException) {
            Result.failure(Exception("No internet connection"))
        } catch (_: IOException) {
            Result.failure(Exception("Network error occurred"))
        } catch (e: HttpException) {
            Result.failure(Exception("Server error: ${e.code()}"))
        } catch (e: Exception) {
            Result.failure(Exception("Unable to find this channel"))
        }
    }

    override suspend fun removeChannel(channel: Channel): Result<String> {
        return try {
            channelDataSource.delete(channel.channelId)
            Result.success(channel.channelId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
