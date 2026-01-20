package com.github.abrarshakhi.mytube.di

import com.github.abrarshakhi.mytube.data.local.cache.DiskThumbnailCache
import com.github.abrarshakhi.mytube.data.local.cache.ThumbnailCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideThumbnailCache(diskThumbnailCache: DiskThumbnailCache): ThumbnailCache =
        diskThumbnailCache
}
