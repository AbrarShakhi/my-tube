package com.github.abrarshakhi.mytube.data.remote

import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object Downloader {
    suspend fun toBytes(url: String): ByteArray? = withContext(Dispatchers.IO) {
        try {
            URL(url).readBytes()
        } catch (_: Exception) {
            null
        }
    }
}