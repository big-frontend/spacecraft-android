package com.hawksjamesf.av.recorder

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.util.Log
import android.view.Surface
import com.hawksjamesf.av.writeFully
import java.io.FileDescriptor
import java.io.IOException
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
class StreamRecorder : Recorder {
    companion object {
        private const val DEFAULT_I_FRAME_INTERVAL = 1 // seconds
        private const val REPEAT_FRAME_DELAY_US = 100000 // repeat after 100ms
        private const val DEFAULT_MAX_FPS = 30
        private const val KEY_MAX_FPS_TO_ENCODER = "max-fps-to-encoder"

        //        private const val DEFAULT_BIT_RATE = 56*1000//kbps=kbits/s
//        private const val DEFAULT_BIT_RATE = 500*1000//kbps=kbits/s
//        private const val DEFAULT_BIT_RATE = 2 * 1000 * 1000//Mbps=Mbits/s
//        private const val DEFAULT_FRAME_RATE = 30

    }

    interface Config {
        val bitRate: Int
        val frameRate: Int
        val width: Int
        val height: Int
    }

    class SD_Low : Config {
        override val bitRate: Int = 56_000//Kbps
        override val frameRate: Int = 12//fps
        override val width: Int = 144
        override val height: Int = 176
    }

    class SD_Hight : Config {
        override val bitRate: Int = 500_000//Kbps
        override val frameRate: Int = 30//fps
        override val width: Int = 360
        override val height: Int = 480
    }

    class HD : Config {
        override val bitRate: Int = 1_800_000//Mbps
        override val frameRate: Int = 25//fps
        override val width: Int = 720
        override val height: Int = 1280
    }
    class Custom :Config{
        override val bitRate: Int
            get() = 1_800_000//Mbps
        override val frameRate: Int
            get() = 15
        override val width: Int
            get() = 1080
        override val height: Int
            get() = 1920
    }

    private lateinit var outputfd: FileDescriptor
    private lateinit var mCodec: MediaCodec
    val surface: Surface by lazy {
        mCodec.createInputSurface()
    }
    private lateinit var mOutputFormat: MediaFormat
    fun setOutput(outputfd: FileDescriptor) = apply {
        this.outputfd = outputfd
    }

    init {
        val config = Custom()
        val bitRate = config.bitRate
        val frameRate = config.frameRate
        val maxFps = DEFAULT_MAX_FPS
        val format = MediaFormat().apply {
            setString(MediaFormat.KEY_MIME, MediaFormat.MIMETYPE_VIDEO_AVC)
            setInteger(MediaFormat.KEY_BIT_RATE, bitRate)
            // must be present to configure the encoder, but does not impact the actual frame rate, which is variable
            setInteger(MediaFormat.KEY_FRAME_RATE, frameRate)
            setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
            setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, DEFAULT_I_FRAME_INTERVAL)
            // display the very first frame, and recover from bad quality when no new frames
            setLong(MediaFormat.KEY_REPEAT_PREVIOUS_FRAME_AFTER, REPEAT_FRAME_DELAY_US.toLong()) // µs
//            if (maxFps > 0) {
//                // The key existed privately before Android 10:
//                // <https://android.googlesource.com/platform/frameworks/base/+/625f0aad9f7a259b6881006ad8710adce57d1384%5E%21/>
//                // <https://github.com/Genymobile/scrcpy/issues/488#issuecomment-567321437>
//                setFloat(KEY_MAX_FPS_TO_ENCODER, maxFps.toFloat())
//            }
            setInteger(MediaFormat.KEY_WIDTH, config.width)
            setInteger(MediaFormat.KEY_HEIGHT, config.height)
        }
        mCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
//        MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
//        var codecList = MediaCodecList(MediaCodecList.REGULAR_CODECS)
//         codecList = MediaCodecList(MediaCodecList.ALL_CODECS)
//        val f = MediaFormat().apply {
//            setString(MediaFormat.KEY_MIME, format.getString(MediaFormat.KEY_MIME))
//        }
//        val findDecoderForFormat = codecList.findDecoderForFormat(f)
//        val findEncoderForFormat = codecList.findEncoderForFormat(f)
//        MediaCodec.createByCodecName(findDecoderForFormat)
//        asyncProcess(codec)
        mCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
    }

    //Asynchronous Processing using Buffers
    private fun asyncProcess(codec: MediaCodec) {
        codec.setCallback(object : MediaCodec.Callback() {
            override fun onOutputBufferAvailable(codec: MediaCodec, index: Int, info: MediaCodec.BufferInfo) {
                Log.d("cjf", "onOutputBufferAvailable index:$index")
//                write(codec,fd,index)
            }

            override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {
                Log.d("cjf", "onInputBufferAvailable index:$index")
                val inputBuffer = codec.getInputBuffer(index);
                // fill inputBuffer with valid data
//                codec.queueInputBuffer(index,)
            }

            override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {
                Log.d("cjf", "onOutputFormatChanged")
                // Subsequent data will conform to new format.
                // Can ignore if using getOutputFormat(outputBufferId)
                mOutputFormat = format // option B
            }

            override fun onError(codec: MediaCodec, e: MediaCodec.CodecException) {
                Log.d("cjf", "onError")
            }

        })
    }

    private fun getSamplesForChannel(codec: MediaCodec, bufferId: Int, channelIx: Int): ShortArray? {
        val outputBuffer: ByteBuffer? = codec.getOutputBuffer(bufferId)
        val format: MediaFormat = codec.getOutputFormat(bufferId)
        val samples: ShortBuffer = outputBuffer?.order(ByteOrder.nativeOrder())?.asShortBuffer()!!
        val numChannels: Int = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
        if (channelIx < 0 || channelIx >= numChannels) {
            return null
        }
        val res = ShortArray(samples.remaining() / numChannels)
        for (i in res.indices) {
            res[i] = samples.get(i * numChannels + channelIx)
        }
        return res
    }

    //Synchronous Processing using Buffers
    private fun syncEncode(codec: MediaCodec, fd: FileDescriptor) {
        var eof = false
        val bufferInfo = MediaCodec.BufferInfo()
        while (!eof) {
            val outputBufferId = codec.dequeueOutputBuffer(bufferInfo, -1)
            eof = bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0
            when {
                outputBufferId >= 0 -> {
                    val outputBuffer = codec.getOutputBuffer(outputBufferId)
                    val bufferFormat = codec.getOutputFormat(outputBufferId)
                    writeFrameMeta(fd, bufferInfo, outputBuffer)
                    writeFully(fd, outputBuffer)
                    codec.releaseOutputBuffer(outputBufferId, false)
                }
                outputBufferId == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                    // Subsequent data will conform to new format.
                    // Can ignore if using getOutputFormat(outputBufferId)
                    mOutputFormat = codec.outputFormat // option B
                }
                outputBufferId == MediaCodec.INFO_TRY_AGAIN_LATER -> {
                }
            }

        }
    }

    private fun parseFrameType(buffer: Int?): Int {
        return if (buffer == null) {
            0
        } else {
            (buffer and 0x60) shr 5
        }
    }

    private val headerBuffer = ByteBuffer.allocate(12)
    private val NO_PTS = -1
    private var ptsOrigin: Long = 0

    @Throws(IOException::class)
    private fun writeFrameMeta(fd: FileDescriptor, bufferInfo: MediaCodec.BufferInfo,outputBuffer:ByteBuffer?) {
        val packetSize = outputBuffer?.remaining() ?: -1
        headerBuffer.clear()
        val pts: Long
        if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
            pts = NO_PTS.toLong() // non-media data packet
        } else {
            if (ptsOrigin == 0L) {
                ptsOrigin = bufferInfo.presentationTimeUs
            }
            pts = bufferInfo.presentationTimeUs - ptsOrigin
        }
        headerBuffer.putLong(pts)
        headerBuffer.putInt(packetSize)
        headerBuffer.flip()
        val ret = parseFrameType(outputBuffer?.get(4)?.toInt())
        when (ret) {
//            0 -> Log.d("cjf", "$pts $packetSize frame type B frame $ret")
//            2 -> Log.d("cjf", "$pts $packetSize frame type P frame $ret")
            3 -> Log.d("cjf", "$pts $packetSize frame type I frame $ret")
        }
        writeFully(fd, headerBuffer)
    }

    fun start() {
        mCodec.start()
        syncEncode(mCodec, outputfd)
    }

    fun stop() = mCodec.stop()

    fun release() {
        mCodec.release()
    }


}