package com.github.abrarshakhi.mytube.presentation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.abrarshakhi.mytube.domain.usecase.SyncVideosUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncVideosWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncVideosUseCase: SyncVideosUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val res = syncVideosUseCase()
        return if (res.isSuccess) {
            Result.success()
        } else {
            Result.retry()
        }
    }
}