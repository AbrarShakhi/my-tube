package com.github.abrarshakhi.mytube.data.repository

import com.github.abrarshakhi.mytube.BuildConfig
import com.github.abrarshakhi.mytube.data.local.datasource.ChannelWithFilterDataSource
import com.github.abrarshakhi.mytube.data.mapper.toDomain
import com.github.abrarshakhi.mytube.data.remote.api.ChannelApi
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import jakarta.inject.Inject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChannelRepositoryImpl @Inject constructor(
    private val channelDataSource: ChannelWithFilterDataSource,
    private val channelApi: ChannelApi
) : ChannelRepository {

    override suspend fun getChannels(): Result<List<Channel>> {
        return Result.success(channelDataSource.getAll().map {
            it.toDomain()
        })
    }

    override suspend fun addChannel(channel: Channel): Result<String> {
        return Result.failure(NotImplementedError("addChannel not implemented"))
    }

    override suspend fun findChannel(handle: String): Result<Channel> {
        return try {
            val response = channelApi.getChannelById(
                handle = handle,
                apiKey = BuildConfig.YOUTUBE_API_KEY
            )

            val channelDto = response.items.firstOrNull()
                ?: return Result.failure(Exception("Unimplemented Exception"))

            Result.success(channelDto.toDomain())

        } catch (_: SocketTimeoutException) {
            Result.failure(Exception("Connection timed out. Please try again."))
        } catch (_: UnknownHostException) {
            Result.failure(Exception("No internet connection"))
        } catch (_: IOException) {
            Result.failure(Exception("Network error occurred"))
        } catch (e: HttpException) {
            Result.failure(Exception("Server error: ${e.code()}"))
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "Unknown error"))
        }
    }


}
