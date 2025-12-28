package com.github.abrarshakhi.mytube.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.abrarshakhi.mytube.data.local.entity.ChannelFilterEntity

@Dao
interface ChannelFilterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilter(filter: ChannelFilterEntity)

    @Query("SELECT * FROM channels_filter WHERE id = :channelId")
    suspend fun getFilter(channelId: Long): ChannelFilterEntity?

    @Query("DELETE FROM channels_filter WHERE id = :channelId")
    suspend fun deleteFilter(channelId: Long)
}
