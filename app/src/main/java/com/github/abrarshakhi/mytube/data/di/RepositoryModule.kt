package com.github.abrarshakhi.mytube.data.di

import com.github.abrarshakhi.mytube.data.repository.ChannelRepositoryImpl
import com.github.abrarshakhi.mytube.domain.repository.ChannelRepository
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
    abstract fun bindChannelRepository(
        impl: ChannelRepositoryImpl
    ): ChannelRepository
}
