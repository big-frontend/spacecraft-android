package com.jamesfchen.av.video;

import android.media.MediaPlayer;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

/**
 * Copyright ® 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/03/2019  Sun
 */
public abstract class OnMediaPlayerListener {
    @MainThread
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @WorkerThread
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @MainThread
    abstract public void onPrepared(MediaPlayer mp);

    @MainThread
    abstract public void onFirstFrame(MediaPlayer mp);

    @MainThread
    public void onSeekComplete(MediaPlayer mp) {

    }

    @MainThread
    abstract public void onCompletion(MediaPlayer mp);

}
