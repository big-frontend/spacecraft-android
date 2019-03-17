package com.hawksjamesf.common.widget;

import android.media.MediaPlayer;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

/**
 * Copyright ® 2019
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/03/2019  Sun
 */
public abstract class OnMediaPlayerListener {
    @MainThread
    protected void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @WorkerThread
    protected void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @MainThread
    abstract protected void onPrepared(MediaPlayer mp);

    @MainThread
    abstract protected void onFirstFrame(MediaPlayer mp);

    @MainThread
    protected void onSeekComplete(MediaPlayer mp) {

    }

    @MainThread
    abstract protected void onCompletion(MediaPlayer mp);

}
