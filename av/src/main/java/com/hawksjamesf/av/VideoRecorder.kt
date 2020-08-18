package com.hawksjamesf.av

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RawRes
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/18/2020  Tue
 */
class VideoRecorder(val context: Context) : SurfaceHolder.Callback {
    companion object {
        private const val TAG = "Chaplin/VideoRecoder"
    }

    val outputFile = File(context.filesDir, "video.mp4")
    val mediaRecorder: MediaRecorder = MediaRecorder()
    private val mCamera: Camera? by lazy {
        return@lazy try {
            Camera.open() // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null // returns null if camera is unavailable
        }
    }
    lateinit var mSurfaceView: SurfaceView
    var mSurfaceHolder: SurfaceHolder? = null

    init {
        Log.d(TAG, "camera:$mCamera")
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mCamera?.apply {
            try {
                setPreviewDisplay(holder)
                startPreview()
            } catch (e: IOException) {
                Log.d("Chaplin/VideoRecoder", "Error setting camera preview: ${e.message}")
            }
        }

        mSurfaceHolder = holder
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        mSurfaceHolder = null
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (holder.surface == null) {
            // preview surface does not exist
            return
        }

        // stop preview before making changes
        try {
            mCamera?.stopPreview()
        } catch (e: Exception) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        mCamera?.apply {
            try {
                setPreviewDisplay(holder)
                startPreview()
            } catch (e: Exception) {
                Log.d("Chaplin/VideoRecoder", "Error starting camera preview: ${e.message}")
            }
        }
    }

    fun start() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
        } catch (e: IllegalStateException) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: ${e.message}")
            releaseMediaRecorder()
        } catch (e: IOException) {
            Log.d(TAG, "IOException preparing MediaRecorder: ${e.message}")
            releaseMediaRecorder()
        }

    }

    fun stop() {
        mediaRecorder.stop()
        mCamera?.release()
        releaseMediaRecorder()
    }


    private fun getOutputMediaFile(type: Int): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp"
        )
        mediaStorageDir.apply {
            if (!exists()) {
                if (!mkdirs()) {
                    Log.d("MyCameraApp", "failed to create directory")
                    return null
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
            else -> null
        }
    }

    private fun releaseMediaRecorder() {
        mediaRecorder?.reset() // clear recorder configuration
        mediaRecorder?.release() // release the recorder object

    }

    private fun bindSurfaceView(surfaceView: SurfaceView) {
        mSurfaceView = surfaceView
        mSurfaceView.holder.addCallback(this)
    }

//    fun setOutput(uri: Uri) {
//        setOutput(uri, null)
//    }

//    fun setOutput(uri: Uri, headers: Map<String, String>?) {
//        mUri = uri
//        mHeaders = headers
//        try {
//            mediaRecorder.reset()
//            mediaRecorder.setDataSource(mContext, mUri, mHeaders)
//            mediaRecorder.prepareAsync()
//            mCurState = State.PREPARING
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

    fun setOutput(@RawRes resid: Int) {
        val afd: AssetFileDescriptor = context.resources.openRawResourceFd(resid) ?: return
        setOutput(afd.fileDescriptor, afd.startOffset, afd.length)
    }

    //For files, it is OK to call prepare(), which blocks until MediaPlayer is ready for playback
    fun setOutput(fd: FileDescriptor?, offset: Long, length: Long) {
        try {
            mediaRecorder.reset()
            mediaRecorder?.run {
                setCamera(mCamera)
                setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
                setVideoSource(MediaRecorder.VideoSource.CAMERA)
                setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH))
//                setOutputFile(fd, offset, length)
                setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString())
//                setVideoSize(720,1080)
//                setVideoFrameRate()
            }.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun createAndBind(context: Context, surfaceView: SurfaceView): VideoRecorder {
        val videoPlayer = VideoRecorder(context)
        videoPlayer.bindSurfaceView(surfaceView)
        return videoPlayer
    }

    @Throws(IOException::class)
    fun createAndBind(context: Context, surfaceView: SurfaceView, @RawRes resid: Int): VideoRecorder {
        val videoPlayer = VideoRecorder(context)
        val afd = context.resources.openRawResourceFd(resid) ?: return videoPlayer
        videoPlayer.bindSurfaceView(surfaceView)
        videoPlayer.setOutput(afd.fileDescriptor, afd.startOffset, afd.length)
        afd.close()
        return videoPlayer
    }

    /**
     * @param context
     * @param resid   R.raw.xxx
     * @return
     */
    @Throws(IOException::class)
    fun create(context: Context, @RawRes resid: Int): VideoRecorder {
        val videoPlayer = VideoRecorder(context)
        val afd = context.resources.openRawResourceFd(resid) ?: return videoPlayer
        videoPlayer.setOutput(afd.fileDescriptor, afd.startOffset, afd.length)
        afd.close()
        return videoPlayer
    }

    fun create(context: Context, uri: Uri): VideoRecorder {
        val videoPlayer = VideoRecorder(context)
        videoPlayer.setOutput(uri)
        return videoPlayer
    }
}