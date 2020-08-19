package com.hawksjamesf.av

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.net.Uri
import androidx.annotation.RawRes
import androidx.core.net.toFile
import java.io.FileDescriptor
import java.io.IOException

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Aug/19/2020  Wed
 */
class AudioPlayer(val context: Context) {
    companion object {
        /**
         * @param context
         * @param resid   R.raw.xxx
         * @return
         */
        fun create(context: Context, @RawRes resid: Int): AudioPlayer {
            val audioPlayer = AudioPlayer(context)
            val afd = context.resources.openRawResourceFd(resid) ?: return audioPlayer
            audioPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            return audioPlayer
        }

        fun create(context: Context, uri: Uri): AudioPlayer {
            val audioPlayer = AudioPlayer(context)
            audioPlayer.setDataSource(uri)
            return audioPlayer
        }
    }

    private val mMediaPlayer: MediaPlayer = MediaPlayer()
    fun setDataSource(@RawRes resid: Int) {
        val afd: AssetFileDescriptor = context.resources.openRawResourceFd(resid) ?: return
        setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
    }
    //For files, it is OK to call prepare(), which blocks until MediaPlayer is ready for playback
    fun setDataSource(fd: FileDescriptor?, offset: Long, length: Long) {
        try {
            mMediaPlayer.reset()
            mMediaPlayer.setDataSource(fd, offset, length)
            mMediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setDataSource(uri: Uri) {
        setDataSource(uri, null)
    }

    // For streams, you should call prepareAsync(), which returns immediately, rather than blocking until enough data has been buffered.
    fun setDataSource(uri: Uri, headers: Map<String, String>?) {
//        mUri = uri
//        mHeaders = headers
        try {
            mMediaPlayer.reset()
//            mMediaPlayer.setDataSource(mContext, mUri, mHeaders)
            mMediaPlayer.prepareAsync()
//            mCurState = State.PREPARING
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}