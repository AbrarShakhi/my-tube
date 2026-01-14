package com.github.abrarshakhi.mytube.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.abrarshakhi.mytube.data.local.entity.VideoEntity

interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)

    @Query("DELETE FROM videos WHERE channelOwnerId = :channelId")
    suspend fun deleteVideosForChannel(channelId: String)
}