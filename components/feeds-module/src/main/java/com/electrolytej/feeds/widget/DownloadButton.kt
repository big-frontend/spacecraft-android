package com.electrolytej.feeds.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.util.SparseIntArray
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.util.getOrDefault
import com.electrolytej.feeds.R

typealias StateChangeListener = (state: Int) -> Unit

class DownloadButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
    defStyleRes: Int = -1
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    companion object {
        const val STATE_READY: Int = 1
        const val STATE_DOWNLOADING: Int = 2
        const val STATE_PAUSE: Int = 3
        const val STATE_FINISH: Int = 4
        const val STATE_RESTART: Int = 5
        //由于Recyclerview会复用ViewHolder所以我们需要记录每个CollapseTextView的状态
        private val stateArray = SparseIntArray()

    }

//    @IntDef(value = [STATE_UNKNOWN, STATE_READY, STATE_START, STATE_PAUSE, STATE_FINISH])
//    annotation class State

    var view: View = inflate(context, R.layout.view_download, this)
    val btStartDownload by lazy { view.findViewById<Button>(R.id.bt_start_download) }
    val llCancelDownload by lazy { view.findViewById<LinearLayout>(R.id.ll_cancel_download) }
    val btRestartDownload by lazy { view.findViewById<Button>(R.id.bt_restart_download) }
    val pbProgress by lazy { view.findViewById<ProgressBar>(R.id.pb_progress) }

    var state: Int = STATE_READY
    private var stateChangeListener: StateChangeListener? = null
    private var key:Int=-1
    init {
        btStartDownload.setOnClickListener {
            state = STATE_DOWNLOADING
            stateChangeListener?.invoke(state)
            Downloader.getInstance().startDownload(key)
            pbProgress.progress = 30
            showCancelDownloadButton()
        }
        llCancelDownload.setOnClickListener {
            state = STATE_READY
            stateChangeListener?.invoke(state)
            Downloader.getInstance().cancelDownload()
            showStartDownloadButton()
        }
        btRestartDownload.setOnClickListener {
            state = STATE_DOWNLOADING
            stateChangeListener?.invoke(state)
            Downloader.getInstance().startDownload(key)
            pbProgress.progress = 30
            showCancelDownloadButton()
        }

        Downloader.getInstance().setOnDownloadListener(object : Downloader.OnDownloadListener {
            override fun onDownloading(key: Int,progress: Int) {
//                pbProgress.post {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        pbProgress.setProgress(progress, true)
                    } else {
                        pbProgress.progress = progress
                    }
//                }
                Log.d("cjf","key:${key} onDownloading:${progress}")
            }

            override fun onFinishDownload() {
                state = STATE_FINISH
                stateChangeListener?.invoke(state)
                Log.d("cjf","onFinishDownload")
            }
        })
    }


    fun setOnStateChangeListener(l: (state: Int) -> Unit) {
        stateChangeListener = l
    }


    /**
     * 使用Recyclerview场景下
     */
    fun autoRecoverStateByUniqueKey(key: Int) {
        this.key = key
        val s = stateArray.getOrDefault(key, STATE_READY)
        this.state = s
        when (state) {
            STATE_READY -> showStartDownloadButton()
            STATE_DOWNLOADING -> showCancelDownloadButton()
            STATE_PAUSE -> {
            }

            STATE_FINISH -> showRestartDownloadButton()
//            STATE_UNKNOWN -> {
//                todo:状态丢失
//            }
        }
        setOnStateChangeListener { state ->
            stateArray.put(key, state)
        }
    }
    private fun showStartDownloadButton() {
        btStartDownload.visibility = VISIBLE
        llCancelDownload.visibility = GONE
        btRestartDownload.visibility = GONE
    }
    private fun showCancelDownloadButton() {
        btStartDownload.visibility = GONE
        llCancelDownload.visibility = VISIBLE
        btRestartDownload.visibility = GONE
    }
    private fun showRestartDownloadButton() {
        btStartDownload.visibility = GONE
        llCancelDownload.visibility = GONE
        btRestartDownload.visibility = VISIBLE
    }
}