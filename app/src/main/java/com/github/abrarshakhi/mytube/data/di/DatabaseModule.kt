package com.github.abrarshakhi.mytube.data.di

import android.content.Context
import androidx.room.Room
import com.github.abrarshakhi.mytube.data.local.AppDatabase
import com.github.abrarshakhi.mytube.data.local.dao.ChannelDao
import com.github.abrarshakhi.mytube.data.local.dao.ChannelFilterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mytube.db"
        ).build()

    @Provides
    fun provideChannelDao(
        database: AppDatabase
    ): ChannelDao = database.channelDao()

    @Provides
    fun provideChannelFilterDao(
        database: AppDatabase
    ): ChannelFilterDao = database.channelFilterDao()
}
