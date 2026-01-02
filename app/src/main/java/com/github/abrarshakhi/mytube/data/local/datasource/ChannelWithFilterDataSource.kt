package com.github.abrarshakhi.mytube.data.local.datasource

import androidx.room.Transaction
import com.github.abrarshakhi.mytube.data.local.dao.ChannelDao
import com.github.abrarshakhi.mytube.data.local.dao.ChannelFilterDao
import com.github.abrarshakhi.mytube.data.local.relation.ChannelWithFilter


class ChannelWithFilterDataSource(
    private val channelDao: ChannelDao,
    private val filterDao: ChannelFilterDao
) {
    @Transaction
    suspend fun insert(channelWithFilter: ChannelWithFilter) {
        filterDao.insertFilter(
            channelWithFilter.filter.copy(
                id = channelDao.insertChannel(channelWithFilter.channel)
            )
        )
    }

    @Transaction
    suspend fun delete(channelId: String) {
        channelDao.deleteChannel(channelId)
    }

    suspend fun getAll(): List<ChannelWithFilter> {
        return try {
            channelDao.getChannelsWithFilters()
        } catch (_: IllegalStateException) {
            emptyList()
        }
    }

    suspend fun get(channelId: String): ChannelWithFilter? {
        return try {
            channelDao.getChannelsWithFilter(channelId)
        } catch (e: IllegalStateException) {
            null
        }
    }
}
