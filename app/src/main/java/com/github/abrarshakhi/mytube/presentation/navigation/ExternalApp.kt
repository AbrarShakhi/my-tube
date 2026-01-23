package com.github.abrarshakhi.mytube.presentation.navigation

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.github.abrarshakhi.mytube.domain.model.Video

object ExternalApp {
    fun openIn(context: Context, video: Video) {
        val appIntent = Intent(Intent.ACTION_VIEW, "vnd.youtube:${video.videoId}".toUri())
        val webIntent = Intent(Intent.ACTION_VIEW, video.videoUrl.toUri())
        try {
            context.startActivity(appIntent)
        } catch (_: Exception) {
            context.startActivity(webIntent)
        }
    }
}