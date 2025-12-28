package com.github.abrarshakhi.mytube.data.remote.api

import com.github.abrarshakhi.mytube.data.remote.dto.ChannelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ChannelApi {

    @GET("channels")
    suspend fun getChannelById(
        @Query("part") part: String = "id,snippet",
        @Query("forHandle") handle: String,
        @Query("key") apiKey: String
    ): ChannelResponse
}
