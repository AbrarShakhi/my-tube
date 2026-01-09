package com.github.abrarshakhi.mytube.data.remote.api

import com.github.abrarshakhi.mytube.data.remote.dto.YoutubeFeed
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeRssApi {
    @GET("feeds/videos.xml")
    suspend fun getLatestVideosByChannelId(
        @Query("channel_id") channelId: String
    ): YoutubeFeed
}