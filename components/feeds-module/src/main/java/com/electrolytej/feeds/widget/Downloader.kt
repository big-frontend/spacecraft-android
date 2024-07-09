package com.electrolytej.feeds.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.util.Log
import android.util.SparseArray

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
                downloadListeners[key].onFinishDownload(key)
                downloadListeners.remove(key)
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        })
        animator.addUpdateListener {
            downloadListeners[key].onDownloading(key,it.animatedValue as Int)
        }
        animator.duration = 10000
        animator.start()

//        Observable.intervalRange(0, 100, 0, 1, TimeUnit.SECONDS)
//            .observeOn(AndroidSchedulers.mainThread()).subscribe({ aLong: Long? ->
//                l.onDownloading(aLong as Int)
//            }, {}, {
//                l.onFinishDownload()
//                downloadListeners.remove(key)
//            })
    }

    fun cancelDownload(key: Int) {
        val downloadListener = downloadListeners.get(key)
        downloadListener?.onCancelDownload()
        downloadListeners.remove(key)
    }

    fun pauseDownload(key: Int) {}
    fun restartDownload(key: Int) {}
    fun setOnDownloadListener(key: Int, downloadListener: OnDownloadListener) {
        downloadListeners[key] = downloadListener
    }

    interface OnDownloadListener {
        fun onDownloading(key: Int,progress: Int)
        fun onFinishDownload(key: Int)
        fun onCancelDownload() {}
    }
}