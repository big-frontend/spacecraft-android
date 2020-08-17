package com.hawksjamesf.av

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaCodec
import android.media.MediaFormat
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_recoder.*
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ShortBuffer


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/15/2020  Sat
 */
class RecoderActivity : Activity() {
    companion object {


        private const val REQUEST_MEDIA_PROJECTION = 1
    }


    var type: Int = 1
    lateinit var mMediaProjectionManager: MediaProjectionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoder)
        val recoder = Recoder
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
        bt_start_recoding.setOnClickListener { theView ->
            requestMediaProjection()

        }
        bt_stop_recoding.setOnClickListener { theView ->
            RecoderService.stopService(this)
            progress.visibility = View.GONE
        }
        bt_play.setOnClickListener { theView ->
            vv_video.setVideoPath(File(filesDir.absolutePath, "video.mp4").absolutePath)
            vv_video.start()
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
            RecoderService.startService(this, service)
            progress.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }



}