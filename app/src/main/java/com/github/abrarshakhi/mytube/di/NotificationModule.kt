package com.github.abrarshakhi.mytube.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {/*
    @Provides
    @Singleton
    fun provideVideoNotifier(videoNotifier: VideoNotifier): VideoNotifier = videoNotifier
*/
}
