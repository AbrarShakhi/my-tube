package com.github.abrarshakhi.mytube.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.abrarshakhi.mytube.data.local.dao.ChannelDao
import com.github.abrarshakhi.mytube.data.local.dao.ChannelFilterDao
import com.github.abrarshakhi.mytube.data.local.dao.VideoDao
import com.github.abrarshakhi.mytube.data.local.entity.ChannelEntity
import com.github.abrarshakhi.mytube.data.local.entity.ChannelFilterEntity
import com.github.abrarshakhi.mytube.data.local.entity.VideoEntity

@Database(
    entities = [ChannelEntity::class, ChannelFilterEntity::class, VideoEntity::class], version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelDao(): ChannelDao
    abstract fun channelFilterDao(): ChannelFilterDao
    abstract fun videoDao(): VideoDao
}
