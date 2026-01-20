package com.github.abrarshakhi.mytube.data.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.github.abrarshakhi.mytube.R
import com.github.abrarshakhi.mytube.domain.model.Video
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VideoNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun notify(video: Video) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, context.packageName)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(video.title)
            .setContentText(video.channel.title)
            .setSubText(video.publishedAt)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setGroup(video.channel.channelId)
            .build()

        manager.notify(video.videoId.hashCode(), notification)
    }
}
