package com.hawksjamesf.common.widget;

import android.media.MediaPlayer;

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
    protected void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    protected void onBufferingUpdate(MediaPlayer mp, int percent) {

    }
    abstract protected void onPrepared(MediaPlayer mp);

    protected void onSeekComplete(MediaPlayer mp) {

    }

    abstract protected void onCompletion(MediaPlayer mp);
}
