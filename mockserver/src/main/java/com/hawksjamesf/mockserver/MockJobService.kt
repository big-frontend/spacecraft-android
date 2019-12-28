package com.hawksjamesf.mockserver

import android.app.job.JobParameters
import android.app.job.JobService
import com.orhanobut.logger.Logger

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/28/2019  Sat
 */
class MockJobService : JobService() {
    companion object{
        private const val TAG = Constants.TAG + "/MockJobService"
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Logger.t(TAG).d("onStartJob")
        return true
    }
    override fun onStopJob(params: JobParameters?): Boolean {
        Logger.t(TAG).d("onStopJob")
        return true
    }

}