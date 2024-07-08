package com.electrolytej.feeds.widget

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class Downloader private constructor() {
    companion object {
        fun getInstance() = InstanceHolder.instance
    }

    private object InstanceHolder {
        val instance = Downloader()
    }

    private var downloadListener: OnDownloadListener? = null
    fun startDownload(key: Int) {
        Observable.intervalRange(1, 100, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { aLong: Long ->
                    downloadListener?.onDownloading(key, aLong.toInt())
                }, {
                }, {
                    downloadListener?.onFinishDownload()
                }
            )
    }

    fun cancelDownload() {}
    fun pauseDownload() {}
    fun restartDownload() {}
    fun setOnDownloadListener(l: OnDownloadListener) {
        downloadListener = l
    }

    interface OnDownloadListener {
        fun onDownloading(key: Int, progress: Int)
        fun onFinishDownload()
    }
}