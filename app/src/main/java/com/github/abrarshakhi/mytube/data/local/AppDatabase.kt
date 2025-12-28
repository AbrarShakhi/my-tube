package com.github.abrarshakhi.mytube.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.abrarshakhi.mytube.data.local.dao.ChannelDao
import com.github.abrarshakhi.mytube.data.local.dao.ChannelFilterDao
import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.local.entity.ChannelFilterEntity

@Database(
    entities = [
        ChannelEntity::class,
        ChannelFilterEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelDao(): ChannelDao
    abstract fun channelFilterDao(): ChannelFilterDao
}
