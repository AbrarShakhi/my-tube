package com.github.abrarshakhi.mytube.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.abrarshakhi.mytube.data.local.entity.VideoEntity
import com.github.abrarshakhi.mytube.data.local.relation.VideoWithChannel
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)

    @Query("DELETE FROM videos WHERE channelOwnerId = :channelId")
    suspend fun deleteVideosForChannel(channelId: String)

    @Query(
        """
    SELECT 
        v.id AS v_id,
        v.videoId AS v_videoId,
        v.channelOwnerId AS v_channelOwnerId,
        v.title AS v_title,
        v.thumbnailUrl AS v_thumbnailUrl,
        v.publishedAt AS v_publishedAt,

        c.id AS c_id,
        c.channelId AS c_channelId,
        c.title AS c_title,
        c.thumbnail AS c_thumbnail,
        c.hasNewUpload AS c_hasNewUpload,
        c.lastUploadedAt AS c_lastUploadedAt
    FROM videos v
    INNER JOIN channels c
        ON v.channelOwnerId = c.channelId
    ORDER BY v.publishedAt DESC
"""
    )
    fun getAllVideos(): Flow<List<VideoWithChannel>>
}