package com.github.abrarshakhi.mytube.data.local.cache

interface ThumbnailCache {
    suspend fun put(key: String, bytes: ByteArray)
    suspend fun get(key: String): ByteArray?
}

