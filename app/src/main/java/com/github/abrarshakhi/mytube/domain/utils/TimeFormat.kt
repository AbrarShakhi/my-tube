package com.github.abrarshakhi.mytube.domain.utils

import kotlin.time.Clock

@OptIn(kotlin.time.ExperimentalTime::class)
fun Long.toTimeSince(): String {
    val nowMillis = Clock.System.now().toEpochMilliseconds()
    val diffMillis = nowMillis - this

    if (diffMillis < 0) return "Just now"

    val seconds = diffMillis / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val weeks = days / 7

    return when {
        seconds < 60 -> "Just now"
        minutes < 60 -> "$minutes minute${plural(minutes)} ago"
        hours < 24 -> "$hours hour${plural(hours)} ago"
        days < 7 -> "$days day${plural(days)} ago"
        weeks < 4 -> "$weeks week${plural(weeks)} ago"
        else -> "Long ago"
    }
}

private fun plural(value: Long) = if (value == 1L) "" else "s"
