package com.electrolytej.download

import android.animation.Animator
import android.animation.ValueAnimator
import android.util.Log
import android.util.SparseArray
import com.liulishuo.okdownload.OkDownload
import com.liulishuo.okdownload.OkDownloadProvider

class Downloader private constructor() {
    companion object {
        private val instance = Downloader()
        fun getInstance(): Downloader {
//            return InstanceHolder.instance
            return instance
        }
    }

    private object InstanceHolder {
        val instance = Downloader()
    }

    private var downloadListeners = SparseArray<OnDownloadListener>()
    fun startDownload(key: Int) {
        val animator = ValueAnimator.ofInt(100)
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationEnd(p0: Animator) {
                Log.d("DownloadButton", "$key onAnimationEnd")
//                downloadListeners[key].onFinishDownload(key)
                downloadListeners.remove(key)
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        })
        animator.addUpdateListener {
            downloadListeners[key]?.onDownloading(key,it.animatedValue as Int)
        }
        animator.duration = 10000
        animator.start()
//        FileDownloader.getImpl().create(url)
//            .setPath(path)
//            .setAutoRetryTimes(3)
//            .setForceReDownload(isForceReDownload)
//            .setCallbackProgressTimes(20) //20次最大回调次数
//            .setListener(new FileDownloadListener ()
    }

    fun pauseDownload(key: Int) {}
    fun restartDownload(key: Int) {}
    fun setOnDownloadListener(key: Int, downloadListener: OnDownloadListener) {
        downloadListeners[key] = downloadListener
    }

    interface OnDownloadListener {
        fun onDownloading(url: Int,progress: Int) {
        }

        fun onPause(url: Int) {
        }

        fun onStop(url: Int) {
        }

        fun onStart(url: Int) {
        }

        fun onFinish(url: Int) {
        }

        fun onFailure(url: Int) {
        }

        fun onSuccess(url: Int) {
        }
    }
}