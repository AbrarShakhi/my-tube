package com.github.abrarshakhi.mytube.data.repository

import com.github.abrarshakhi.mytube.BuildConfig
import com.github.abrarshakhi.mytube.data.local.cache.ThumbnailCache
import com.github.abrarshakhi.mytube.data.local.datasource.DatabaseSource
import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.local.entity.ChannelFilterEntity
import com.github.abrarshakhi.mytube.data.local.entity.VideoEntity
import com.github.abrarshakhi.mytube.data.local.relation.ChannelWithVideos
import com.github.abrarshakhi.mytube.data.mapper.toDomain
import com.github.abrarshakhi.mytube.data.mapper.toEntity
import com.github.abrarshakhi.mytube.data.mapper.toRelation
import com.github.abrarshakhi.mytube.data.notification.VideoNotifier
import com.github.abrarshakhi.mytube.data.remote.Downloader
import com.github.abrarshakhi.mytube.data.remote.api.YoutubeApi
import com.github.abrarshakhi.mytube.data.remote.api.YoutubeRssApi
import com.github.abrarshakhi.mytube.data.utils.networkCall
import com.github.abrarshakhi.mytube.domain.model.Channel
import com.github.abrarshakhi.mytube.domain.model.Video
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import com.github.abrarshakhi.mytube.domain.repository.SyncRepository
import com.github.abrarshakhi.mytube.domain.repository.VideoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

class MyTubeRepositoryImpl @Inject constructor(
    private val dbSource: DatabaseSource,
    private val youtubeApi: YoutubeApi,
    private val youtubeRssApi: YoutubeRssApi,
    private val thumbnailCache: ThumbnailCache,
    private val videoNotifier: VideoNotifier
) : ChannelRepository, SyncRepository, VideoRepository {

    override suspend fun getChannels(): Result<List<Channel>> {
        return Result.success(dbSource.getAllChannel().map {
            it.toDomain()
        })
    }

    override suspend fun addChannel(channel: Channel): Result<Unit> {
        try {
            dbSource.insertChannel(channel.toRelation())
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun findChannel(handle: String): Result<Channel> {
        val response = networkCall {
            youtubeApi.getChannelById(
                handle = handle, apiKey = BuildConfig.YOUTUBE_API_KEY
            )
        }.getOrElse { error -> return Result.failure(error) }

        val channelDto =
            response.items.firstOrNull() ?: return Result.failure(Exception("Channel Not found"))

        val thumbnailBytes: ByteArray? = channelDto.snippet.thumbnails.default?.url?.let { url ->
            Downloader.toBytes(url)
        }

        return Result.success(channelDto.toDomain(thumbnailBytes))
    }

    override suspend fun removeChannel(channel: Channel): Result<Unit> {
        return try {
            dbSource.deleteChannel(channel.channelId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun syncVideos(): Result<Unit> = coroutineScope {
        try {
            val dispatcher = Dispatchers.IO.limitedParallelism(4)
            if (dbSource.getAllChannel().map { (channel, filter) ->
                    return@map async(dispatcher) {
                        val newVideos = fetchVideoForChannel(channel, filter).getOrThrow()
                        if (newVideos.isEmpty()) {
                            return@async false
                        }

                        dbSource.insertVideos(ChannelWithVideos(channel, newVideos))
                        newVideos.forEach { videoEntity ->
                            val key = videoEntity.videoId
                            val thumbnailBytes = thumbnailCache.get(key)
                                ?: videoEntity.thumbnailUrl?.let { Downloader.toBytes(it) }
                                    ?.also { thumbnailCache.put(key, it) }
                            videoNotifier.notify(
                                videoEntity.toDomain(
                                    channel = channel, thumbnail = thumbnailBytes
                                )
                            )
                        }
                        return@async true
                    }
                }.awaitAll().any { it }) {
                TODO("Send custom broadcast")
            }
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }


    suspend fun fetchVideoForChannel(
        channel: ChannelEntity, filter: ChannelFilterEntity
    ): Result<List<VideoEntity>> {
        val ytFeed = networkCall {
            youtubeRssApi.getLatestVideosByChannelId(channel.channelId)
        }.getOrElse { error ->
            return Result.failure(error)
        }
        val videoEntriesDto = ytFeed.entries ?: return Result.failure(Exception())

        val videoEntities: MutableList<VideoEntity> = mutableListOf()
        for (videoEntry in videoEntriesDto) {
            videoEntities.add(videoEntry.toEntity(channel.channelId) ?: continue)
        }
        videoEntities.sortByDescending { it.publishedAt }

        val regex = Regex(filter.regex)
        val lastUpload = channel.lastUploadedAt ?: -1L

        val newVideos = videoEntities.filter {
            it.publishedAt > lastUpload && (regex.containsMatchIn(it.title) == filter.contains)
        }
        return Result.success(newVideos)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getVideos(): Flow<List<Video>> = dbSource.getAllVideos().mapLatest { list ->
        withContext(Dispatchers.IO) {
            list.map { it.toDomain(thumbnailCache.get(it.video.videoId)) }
        }
    }
}
