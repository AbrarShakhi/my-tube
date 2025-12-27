package com.github.abrarshakhi.mytube.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity

@Dao
interface ChannelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannel(channel: ChannelEntity)

    @Query("SELECT * FROM channels WHERE channelId = :channelId LIMIT 1")
    suspend fun getChannel(channelId: String): ChannelEntity?

    @Query("SELECT * FROM channels")
    suspend fun getChannels(): List<ChannelEntity>


}
