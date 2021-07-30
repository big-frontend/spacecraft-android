package com.jamesfchen.av

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.widget.Toast
import com.jamesfchen.av.databinding.ActivityRecoderBinding
import java.io.File
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*


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
    private lateinit var binding: ActivityRecoderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mMediaProjectionManager = applicationContext.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        binding.rgType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_audio -> {
                    type = 0
                }
                R.id.rb_video -> {
                    type = 1
                }
            }
        }
        var outputFile = File(getExternalFilesDir(null), "video.mp4")
        if (!outputFile.exists()) {
            outputFile.createNewFile()
        }
        val videoRecoder = VideoRecorder.createAndBindCameraFacingBack(this,outputFile,  binding.svPreview)
        binding.btStartRecodingCamera.setOnClickListener { theView ->
            videoRecoder.start()
        }
        binding.btStopRecodingCamera.setOnClickListener { theView ->
            Toast.makeText(this@RecorderActivity, "file size: ${outputFile.length()}", Toast.LENGTH_LONG).show()
            videoRecoder.stop()
        }
        binding.btStartRecodingSceen.setOnClickListener { theView ->
            requestMediaProjection()
        }
        binding.btStopRecodingSceen.setOnClickListener { theView ->
            RecorderService.stopService(this)

        }
        binding.btPlay.setOnClickListener { theView ->
//            binding.clvVideo.setDataSourceAndPlay(outputFile)
//        vv_video.setVideoPath(outputFile.absolutePath)
//            vv_video.start()
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun getOutputMediaFile(type: Int): File {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp"
        )
        mediaStorageDir.apply {
            if (!exists()) {
                if (!mkdirs()) {
                    throw  IllegalArgumentException("failed to create directory")
                }
            }
        }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return when (type) {
            MEDIA_TYPE_IMAGE -> {
                File("${mediaStorageDir.path}${File.separator}IMG_$timeStamp.jpg")
            }
            MEDIA_TYPE_VIDEO -> {
                File("${mediaStorageDir.path}${File.separator}VID_$timeStamp.mp4")
            }
            else -> throw  IllegalArgumentException("no tyep")
        }
    }


}