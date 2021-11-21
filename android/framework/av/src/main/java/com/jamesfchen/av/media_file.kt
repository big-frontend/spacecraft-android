@file:JvmName("MediaFileUtil")

package com.jamesfchen.av

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaCodecInfo
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaFormat.*
import android.util.Log
import androidx.annotation.RawRes
import java.nio.ByteBuffer

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/20/2020  Thu
 */
fun MediaExtractor.open(ctxt: Context, @RawRes resid: Int) {
    val afd: AssetFileDescriptor = ctxt.resources.openRawResourceFd(resid) ?: return
    setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
}

fun MediaExtractor.readAudioBuffer(buffer: ByteBuffer) {
    for (trackIndex in 0 until trackCount) {
        val mime = getTrackFormat(trackIndex).getString(KEY_MIME)
        mime?.takeIf { it.startsWith("audio") }?.run {
            readBuffer(trackIndex, buffer)
        }
    }

}

fun MediaExtractor.readVideoBuffer(buffer: ByteBuffer) {
    for (trackIndex in 0 until trackCount) {
        val mime = getTrackFormat(trackIndex).getString(KEY_MIME)
        mime?.takeIf { it.startsWith("video") }?.run {
            readBuffer(trackIndex, buffer)
        }
    }
}

fun MediaExtractor.readTotalBuffer(buffer: ByteBuffer) {
    readBuffer(-1, buffer)
}

private fun MediaExtractor.readBuffer(index: Int = -1, buffer: ByteBuffer) {
    if (index <= 0) {
        selectTrack(0)
    } else {
        selectTrack(index)
    }
    while (readSampleData(buffer, 0) >= 0) {
        if (index != -1 && index != sampleTrackIndex) break
        val presentationTimeUs = sampleTime
        Log.d("cjf", " ${sampleTrackIndex} ${presentationTimeUs} ${sampleFlags}")
        advance()
    }
}

data class Formats(
        val videof: MediaFormat? = null,
        val audiof: MediaFormat? = null)

fun MediaExtractor.findFormats(): Formats {
    var videof: MediaFormat? = null
    var audiof: MediaFormat? = null
    for (trackIndex in 0 until trackCount) {
        val f = getTrackFormat(trackIndex)
        f.getString(KEY_MIME)?.run {
            Log.d("cjf", "mime:${this}")
            if (this.startsWith("video")) {
                videof = f
            } else if (this.startsWith("audio")) {
                audiof = f
            }
        }
    }
    return Formats(videof, audiof)
}
fun main(args: Array<String>) {
//    val extractor = MediaExtractor()
//    extractor.open(this, R.raw.wechatsight1)
//    val (videof, audiof) = extractor.findFormats()
//    val allocate = ByteBuffer.allocate(1000 * 1024)
//    extractor.readAudioBuffer(allocate)
//    extractor.readVideoBuffer(allocate)
//    extractor.readTotalBuffer(allocate)
//    extractor.release()
//    extractor.cachedDuration
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        extractor.drmInitData
//    }
//    extractor.getSampleCryptoInfo()
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        extractor.setMediaCas()
//    }
//    val psshInfo = extractor.psshInfo
    //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//        val audioPresentations = extractor.getAudioPresentations(trackIndex)
//        extractor.sampleSize
//
//    }
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val casInfo = extractor.getCasInfo(trackIndex)
//        extractor.metrics
//    }
//    extractor.unselectTrack(trackIndex)

    val videoFormat = createVideoFormat(MIMETYPE_VIDEO_AVC, 1280, 720)
    val header_sps = byteArrayOf(0, 0, 0, 1, 103, 100, 0, 31, -84, -76, 2, -128, 45, -56)
    val header_pps = byteArrayOf(0, 0, 0, 1, 104, -18, 60, 97, 15, -1, -16, -121, -1, -8, 67, -1, -4, 33, -1, -2, 16, -1, -1, 8, 127, -1, -64)
    videoFormat.setByteBuffer("csd-0", ByteBuffer.wrap(header_sps));
    videoFormat.setByteBuffer("csd-1", ByteBuffer.wrap(header_pps));
    videoFormat.setInteger(KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
    videoFormat.setInteger(KEY_MAX_INPUT_SIZE, 1920 * 1080);
    videoFormat.setInteger(KEY_CAPTURE_RATE, 25);
    val audioFormat: MediaFormat = createAudioFormat(MIMETYPE_AUDIO_MPEG, 100, 1)
//    MediaFormat.createSubtitleFormat(MIMETYPE_TEXT_VTT)
    val metadataFormat = MediaFormat()
    metadataFormat.setString(KEY_MIME, "application/gyro")

//    val latitude: Float? = null
//    var  longitude: Float? = null
//    val muxer = MediaMuxer(output, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
//    if (latitude != null && longitude != null) {
//        muxer.setLocation(latitude, longitude)
//    }
//    val addTrack= muxer.ddTrack(f)
//    muxer.start()
//    muxer.stop()
//    muxer.release()
//    muxer.setOrientationHint()
//    output("temp.mp4", videoFormat, audioFormat, metadataFormat, block = { muxer ->
//        val bufferInfo = MediaCodec.BufferInfo()
//        var finished = false
//        while (!finished) {
//            finished = getInputBuffer(inputBuffer, isAudioSample, bufferInfo)
//            bufferInfo.offset = 0
//            bufferInfo.size = sampleSize
//            bufferInfo.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME
//            bufferInfo.presentationTimeUs = mVideoExtractor.getSampleTime()
//            if (!finished) {
//                muxer.writeSampleData(avTrackIndex, inputBuffer, bufferInfo)
//            }
//        }
//    })
}