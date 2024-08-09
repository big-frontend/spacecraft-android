package com.electrolytej.feeds.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.SparseIntArray
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.util.getOrDefault
import com.electrolytej.download.Downloader
import com.electrolytej.feeds.R
import com.electrolytej.download.Downloader.OnDownloadListener

typealias StateChangeListener = (state: Int) -> Unit

class DownloadButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1, defStyleRes: Int = -1
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    companion object {
        private const val TAG = "DownloadButton"
        const val STATE_READY = 1
        const val STATE_DOWNLOADING = 2
        const val STATE_PAUSE = 3
        const val STATE_FINISH = 4
        //由于Recyclerview会复用ViewHolder所以我们需要记录每个状态
        private val stateArray = SparseIntArray()
    }
//    @IntDef(value = [STATE_UNKNOWN, STATE_READY, STATE_START, STATE_PAUSE, STATE_FINISH])
//    annotation class State
    private val view: View = inflate(context, R.layout.view_download, this)
    private val btStartDownload by lazy { view.findViewById<Button>(R.id.bt_start_download) }
    private val llCancelDownload by lazy { view.findViewById<LinearLayout>(R.id.ll_cancel_download) }
    private val btRestartDownload by lazy { view.findViewById<Button>(R.id.bt_restart_download) }
    private val btCancelDownload by lazy { view.findViewById<Button>(R.id.bt_cancel_download) }
    private val pbProgress by lazy { view.findViewById<ProgressBar>(R.id.pb_progress) }

    var state = STATE_READY
    private var stateChangeListener: StateChangeListener? = null
    private var l: OnDownloadListener? = null
    private var key: Int = -1
    init {
        btStartDownload.setOnClickListener {
            pbProgress.progress = 0
            toDownloadingState()
            Downloader.getInstance().startDownload(key)
        }
        btCancelDownload.setOnClickListener {
            toReadyState()
            Downloader.getInstance().pauseDownload(key)
        }
        btRestartDownload.setOnClickListener {
            pbProgress.progress = 0
            toDownloadingState()
            Downloader.getInstance().startDownload(key)
        }
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
        Log.d(TAG, "autoRecoverStateByUniqueKey key:${key}/$s")
        when (s) {
            STATE_READY -> toReadyState()
            STATE_DOWNLOADING -> toDownloadingState()
            STATE_FINISH -> toFinishState()
        }
        //todo:RecyclerView 的 ViewHolder 复用机制导致下载监听回调错误
//        Downloader.getInstance().setOnDownloadListener(key,object : OnDownloadListener {
//            override fun onDownloading(k: Int,progress: Int) {
//                Log.d(TAG, "${this@DownloadButton.key} $k onDownloading")
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    pbProgress.setProgress(progress, true)
//                } else {
//                    pbProgress.progress = progress
//                }
//                btCancelDownload.setText("取消下载 ${progress}%")
//            }
//
//            override fun onFinishDownload(k: Int) {
//                Log.d(TAG, "${this@DownloadButton.key} $k onFinishDownload")
//                toFinishState()
//            }
//        })
    }

    private fun toReadyState() {
        state = STATE_READY
        stateChangeListener?.invoke(state)
        stateArray.put(key, state)
        btStartDownload.visibility = VISIBLE
        llCancelDownload.visibility = GONE
        btRestartDownload.visibility = GONE
    }

    private fun toDownloadingState() {
        state = STATE_DOWNLOADING
        stateChangeListener?.invoke(state)
        stateArray.put(key, state)
        btStartDownload.visibility = GONE
        llCancelDownload.visibility = VISIBLE
        btRestartDownload.visibility = GONE
    }

    private fun toFinishState() {
        state = STATE_FINISH
        stateChangeListener?.invoke(state)
        stateArray.put(key, state)
        btStartDownload.visibility = GONE
        llCancelDownload.visibility = GONE
        btRestartDownload.visibility = VISIBLE
    }
}