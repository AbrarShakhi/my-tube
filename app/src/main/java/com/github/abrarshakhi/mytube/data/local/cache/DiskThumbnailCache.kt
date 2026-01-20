package com.github.abrarshakhi.mytube.data.local.cache

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class DiskThumbnailCache @Inject constructor(@ApplicationContext private val context: Context) :
    ThumbnailCache {

    private val cacheDir = File(context.cacheDir, "thumbnails").apply { mkdirs() }

    override suspend fun put(key: String, bytes: ByteArray) = withContext(Dispatchers.IO) {
        fileForKey(key).writeBytes(bytes)
    }

    override suspend fun get(key: String): ByteArray? = withContext(Dispatchers.IO) {
        val file = fileForKey(key)
        if (file.exists()) file.readBytes() else null
    }

    private fun fileForKey(key: String): File {
        val safeName = key.hashCode().toString()
        return File(cacheDir, safeName)
    }
}
