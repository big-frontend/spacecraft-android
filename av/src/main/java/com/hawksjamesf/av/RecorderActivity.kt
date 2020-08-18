package com.hawksjamesf.av

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_recoder.*
import java.io.File
import kotlin.concurrent.thread


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/15/2020  Sat
 */
class RecorderActivity : Activity() {
    companion object {
        private const val REQUEST_MEDIA_PROJECTION = 1
    }


    var type: Int = 1
    lateinit var mMediaProjectionManager: MediaProjectionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoder)
        val recoder = Recorder
        mMediaProjectionManager = applicationContext.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        rg_type.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_audio -> {
                    type = 0
                }
                R.id.rb_video -> {
                    type = 1
                }
            }
        }
        val videoRecoder = VideoRecorder(this@RecorderActivity)
        videoRecoder.bindSurfaceView(sv_preview)
        bt_start_recoding.setOnClickListener { theView ->
//            requestMediaProjection()
//           thread {
               videoRecoder.start()
//           }
        }
        bt_stop_recoding.setOnClickListener { theView ->
//            RecorderService.stopService(this)
//            progress.visibility = View.GONE
            thread {

            videoRecoder.stop()
            }
        }
        bt_play.setOnClickListener { theView ->
//            clv_video.setVideoPath()
            File(filesDir.absolutePath, "video.mp4").absolutePath
            clv_video.setDataSourceAndPlay(R.raw.wechatsight1)
//            bt_play.setText( "size:${recoder.fileSize()} ")
        }

    }

    private fun requestMediaProjection() {
        val captureIntent = mMediaProjectionManager.createScreenCaptureIntent()
        startActivityForResult(captureIntent, REQUEST_MEDIA_PROJECTION)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            val service = Intent()
            service.putExtra("code", resultCode)
            service.putExtra("data", data)
            RecorderService.startService(this, service)
            progress.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }



}