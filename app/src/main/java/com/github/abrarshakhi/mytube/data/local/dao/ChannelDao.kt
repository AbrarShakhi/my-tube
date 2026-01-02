package com.github.abrarshakhi.mytube.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.local.relation.ChannelWithFilter

@Dao
interface ChannelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannel(channel: ChannelEntity): Long

    @Query("SELECT * FROM channels WHERE channelId = :channelId LIMIT 1")
    suspend fun getChannel(channelId: String): ChannelEntity?

    @Query("SELECT * FROM channels")
    suspend fun getChannels(): List<ChannelEntity>

    @Query("DELETE FROM channels WHERE channelId = :channelId")
    suspend fun deleteChannel(channelId: String)

    @Throws(IllegalStateException::class)
    @Transaction
    @Query("SELECT * FROM channels")
    suspend fun getChannelsWithFilters(): List<ChannelWithFilter>

    @Throws(IllegalStateException::class)
    @Transaction
    @Query("SELECT * FROM channels WHERE channelId = :channelId")
    suspend fun getChannelsWithFilter(channelId: String): ChannelWithFilter?
}
