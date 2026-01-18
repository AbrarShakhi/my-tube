package com.github.abrarshakhi.mytube.data.local.datasource

import androidx.room.Transaction
import com.github.abrarshakhi.mytube.data.local.dao.ChannelDao
import com.github.abrarshakhi.mytube.data.local.dao.ChannelFilterDao
import com.github.abrarshakhi.mytube.data.local.dao.VideoDao
import com.github.abrarshakhi.mytube.data.local.relation.ChannelWithFilter
import com.github.abrarshakhi.mytube.data.local.relation.ChannelWithVideos
import com.github.abrarshakhi.mytube.data.local.relation.VideoWithChannel
import kotlinx.coroutines.flow.Flow


class DatabaseSource(
    private val channelDao: ChannelDao,
    private val filterDao: ChannelFilterDao,
    private val videoDao: VideoDao
) {
    @Transaction
    suspend fun insertChannel(channelWithFilter: ChannelWithFilter) {
        filterDao.insertFilter(
            channelWithFilter.filter.copy(
                id = channelDao.insertChannel(channelWithFilter.channel)
            )
        )
    }

    @Transaction
    suspend fun deleteChannel(channelId: String) {
        channelDao.deleteChannel(channelId)
    }

    suspend fun getAllChannel(): List<ChannelWithFilter> {
        return try {
            channelDao.getChannelsWithFilters()
        } catch (_: IllegalStateException) {
            emptyList()
        }
    }

    suspend fun getChannel(channelId: String): ChannelWithFilter? {
        return try {
            channelDao.getChannelsWithFilter(channelId)
        } catch (e: IllegalStateException) {
            null
        }
    }

    suspend fun getVideos(channelId: String): ChannelWithVideos? {
        return try {
            channelDao.getChannelWithVideos(channelId)
        } catch (e: IllegalStateException) {
            null
        }
    }

    @Transaction
    fun getAllVideos(): Flow<List<VideoWithChannel>> {
        return videoDao.getAllVideos()
    }

    @Transaction
    suspend fun insertVideos(channelWithVideos: ChannelWithVideos) {
        videoDao.insertVideos(channelWithVideos.videos)
        channelDao.insertChannel(channelWithVideos.channel)
    }

}
