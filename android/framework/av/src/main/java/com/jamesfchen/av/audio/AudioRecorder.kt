package com.jamesfchen.av.audio

import android.media.MediaRecorder

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/18/2020  Tue
 */
class AudioRecorder {
    init {
        val recorder =  MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        recorder.setOutputFile(PATH_NAME)
        recorder.prepare();
        recorder.start();   // Recording is now started
        recorder.stop();
        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        recorder.release(); // Now the object cannot be reused
    }

    /**
     * @param context
     * @param resid   R.raw.xxx
     * @return
     */
//    @Throws(IOException::class)
//    fun create(context: Context, @RawRes resid: Int): VideoRecorder {
//        val videoPlayer = VideoRecorder(context)
//        val afd = context.resources.openRawResourceFd(resid) ?: return videoPlayer
//        videoPlayer.setOutput(afd.fileDescriptor, afd.startOffset, afd.length)
//        afd.close()
//        return videoPlayer
//    }

//    fun create(context: Context, uri: Uri): VideoRecorder {
//        val videoPlayer = VideoRecorder(context)
////        videoPlayer.setOutput(uri)
//        return videoPlayer
//    }
}