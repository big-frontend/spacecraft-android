package com.hawksjamesf.av.recorder

import android.hardware.display.VirtualDisplay
import android.media.*
import android.util.Log
import android.view.Surface
import com.hawksjamesf.av.writeFully
import java.io.File
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

        private const val DEFAULT_I_FRAME_INTERVAL = 10 // seconds
        private const val REPEAT_FRAME_DELAY_US = 100000 // repeat after 100ms
        private const val KEY_MAX_FPS_TO_ENCODER = "max-fps-to-encoder"
        private const val OK = 0
        private const val ERROR_INPUT_INVALID = 100
        private const val ERROR_OUTPUT_FAILED = 200
        private const val ERROR_OPEN_CODEC = 300
    }


    private lateinit var display: VirtualDisplay
    private lateinit var surface: Surface
    private lateinit var outputFile: File
    fun setOutput(outputfd: FileDescriptor) {

    }

    fun setOutput(outputPath: String) = setOutput(File(outputPath))
    fun setOutput(outputFile: File) {
        if (!outputFile.exists()) {
            outputFile.createNewFile()
        }
        this.outputFile = outputFile
    }

    fun setDisplay(display: VirtualDisplay) {
        surface = codec.createInputSurface()
        display.surface = surface
    }

    var codec: MediaCodec

    private var mOutputFormat: MediaFormat? = null
    private val mMuxer: MediaMuxer by lazy {
        MediaMuxer(outputFile.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
    }


    init {
        val bitRate = 8
        val maxFps = 15
        val format = MediaFormat().apply {
            setString(MediaFormat.KEY_MIME, MediaFormat.MIMETYPE_VIDEO_AVC)
            setInteger(MediaFormat.KEY_BIT_RATE, bitRate)
            // must be present to configure the encoder, but does not impact the actual frame rate, which is variable
            setInteger(MediaFormat.KEY_FRAME_RATE, 60)
            setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
            setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, DEFAULT_I_FRAME_INTERVAL)
            // display the very first frame, and recover from bad quality when no new frames
            setLong(MediaFormat.KEY_REPEAT_PREVIOUS_FRAME_AFTER, REPEAT_FRAME_DELAY_US.toLong()) // µs
            if (maxFps > 0) {
                // The key existed privately before Android 10:
                // <https://android.googlesource.com/platform/frameworks/base/+/625f0aad9f7a259b6881006ad8710adce57d1384%5E%21/>
                // <https://github.com/Genymobile/scrcpy/issues/488#issuecomment-567321437>
                setFloat(KEY_MAX_FPS_TO_ENCODER, maxFps.toFloat())
            }
            setInteger(MediaFormat.KEY_WIDTH, 1080)
            setInteger(MediaFormat.KEY_HEIGHT, 1920)
        }
        codec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
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
        codec?.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
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


    //Synchronous Processing using Buffers
    private fun syncEncode(codec: MediaCodec, fd: FileDescriptor) {
        var eof = false
        val bufferInfo = MediaCodec.BufferInfo()
        while (!eof) {
            val outputBufferId = codec.dequeueOutputBuffer(bufferInfo, -1)
            when {
                outputBufferId >= 0 -> {
                    val outputBuffer = codec.getOutputBuffer(outputBufferId)
                    val bufferFormat = codec.getOutputFormat(outputBufferId)
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

            eof = bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0
        }
    }


    private fun syncDecode(codec: MediaCodec, fd: FileDescriptor) {
        var eof = false
        while (!eof) {
            val inputBufferId = codec.dequeueInputBuffer(-1)
//            eof = read(codec,fd,inputBufferId)
        }
    }

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
    private lateinit var mFormat: MediaFormat
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
                    mFormat = format
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

    fun start() {
        if (outputFile == null) return
        codec.start()
//        mMuxer.addTrack()
//        mMuxer.start()
        syncEncode(codec, outputFile?.outputStream()!!.fd)
    }

    fun fileSize() = outputFile?.length()

    fun stop() = codec.stop()

    fun release() {
        surface.release()
        display?.release()
        codec.release()
        outputFile?.deleteOnExit()
        mMuxer.stop()
        mMuxer.release()
    }


}