package com.github.abrarshakhi.mytube.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.abrarshakhi.mytube.presentation.worker.SyncScheduler

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        SyncScheduler.schedule(context)
    }
}