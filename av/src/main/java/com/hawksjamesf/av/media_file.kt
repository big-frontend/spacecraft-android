package com.hawksjamesf.av

import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import com.hawksjamesf.av.recorder.StreamRecorder
import java.io.FileDescriptor
import java.io.IOException

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/20/2020  Thu
 */
private const val ERROR_INPUT_INVALID = 100
private const val ERROR_OUTPUT_FAILED = 200
private const val ERROR_OPEN_CODEC = 300
private const val OK = 0
private fun read(codec: MediaCodec, fd: FileDescriptor, inputBufferId: Int) {
    if (inputBufferId >= 0) {
        val inputBuffer = codec.getInputBuffer(inputBufferId)
        inputBuffer?.clear()
//            readFully(fd, inputBuffer)
        val sampleSize: Int = mExtractor.readSampleData(inputBuffer!!, 0)
        if (sampleSize < 0) { //read end
            codec.queueInputBuffer(inputBufferId, 0, 0, 0L,
                    MediaCodec.BUFFER_FLAG_END_OF_STREAM)
        } else {
            codec.queueInputBuffer(inputBufferId, 0, sampleSize, mExtractor.getSampleTime(), 0)
            mExtractor.advance()
        }
    }

}
private lateinit var mExtractor: MediaExtractor

//    private val mMuxer: MediaMuxer by lazy {
//        MediaMuxer(outputFile.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
//    }

private fun openInput(audioPath: String): Int {
    var ret: Int
//        if (OK !== checkPath(audioPath).also { ret = it }) {
//            return ret
//        }
    mExtractor = MediaExtractor()
    var audioTrack = -1
    var hasAudio = false
    try {
        mExtractor.setDataSource(audioPath)
        for (i in 0 until mExtractor.trackCount) {
            val format: MediaFormat = mExtractor.getTrackFormat(i)
            val mime = format.getString(MediaFormat.KEY_MIME)
            if (mime.startsWith("audio/")) {
                audioTrack = i
                hasAudio = true
//                mFormat = format
                break
            }
        }
        if (!hasAudio) {
            return ERROR_INPUT_INVALID
        }
        mExtractor.selectTrack(audioTrack)
    } catch (e: IOException) {
        return ERROR_INPUT_INVALID
    }
    return OK
}