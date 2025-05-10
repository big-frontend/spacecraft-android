package com.electrolytej.bundle2.page.workmanager.workers

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.electrolytej.bundle2.page.workmanager.Constants
import java.io.File

/**
 * Cleans up temporary files from the output folder.
 */
class CleanupWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    companion object {
        private const val TAG = "CleanupWorker"
    }

    override fun doWork(): Result {
        try {
            val outputDirectory = File(applicationContext.filesDir, Constants.OUTPUT_PATH)
            if (outputDirectory.exists()) {
                val entries = outputDirectory.listFiles()
                if (entries != null && entries.isNotEmpty()) {
                    for (entry in entries) {
                        val name = entry.name
                        if (!TextUtils.isEmpty(name) && name.endsWith(".png")) {
                            val deleted = entry.delete()
                            Log.i(TAG, String.format("Deleted %s - %s", name, deleted))
                        }
                    }
                }
            }
            return Result.success()
        } catch (exception: Exception) {
            Log.e(TAG, "Error cleaning up", exception)
            return Result.failure()
        }
    }
}
