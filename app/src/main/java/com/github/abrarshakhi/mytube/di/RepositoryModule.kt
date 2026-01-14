package com.github.abrarshakhi.mytube.di

import com.github.abrarshakhi.mytube.data.repository.MyTubeRepositoryImpl
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
import com.github.abrarshakhi.mytube.domain.repository.SyncRepository
import com.github.abrarshakhi.mytube.domain.repository.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChannelRepository(impl: MyTubeRepositoryImpl)
            : ChannelRepository

    @Binds
    @Singleton
    abstract fun bindSyncRepository(impl: MyTubeRepositoryImpl)
            : SyncRepository

    @Binds
    @Singleton
    abstract fun bindVideoRepository(impl: MyTubeRepositoryImpl)
            : VideoRepository
}
