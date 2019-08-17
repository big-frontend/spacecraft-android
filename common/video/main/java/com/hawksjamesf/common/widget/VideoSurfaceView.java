package com.hawksjamesf.common.widget;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.Map;

import androidx.annotation.AnyThread;
import androidx.annotation.RequiresApi;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/02/2019  Sat
 */
@Deprecated
public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener {

    private static final String TAG = "Chaplin/Surface";

    public int surfaceWidth;
    public int surfaceHeight;
    public int videoWidth;
    public int videoHeight;
    private boolean hasStickyMessage = false;
    private State mCurState = State.IDLE;
    private Uri mUri;
    private Map<String, String> mHeaders;

    private int mAudioSessionId;
    private AudioAttributes mAudioAttributes;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;

    private OnLogListener mLogListener;

    public void setOnLogListener(OnLogListener logListener) {
        this.mLogListener = logListener;
    }

    private OnMediaPlayerListener mOnMediaPlayerListener;

    public void setOnMediaPlayerListener(OnMediaPlayerListener listener) {
        mOnMediaPlayerListener = listener;

    }

    public VideoSurfaceView(Context context) {
        super(context);
        initView(null, 0);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs, 0);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(AttributeSet attributeSet, int defStyleAttr) {
        getHolder().addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated:" +
                "surface is creating:" + surfaceHolder.isCreating()
        );
        mSurfaceHolder = surfaceHolder;
        release(false);

        mMediaPlayer = MediaPlayer.create(getContext(), mUri);
        mMediaPlayer.setDisplay(mSurfaceHolder);


        mMediaPlayer.setScreenOnWhilePlaying(true);
        mMediaPlayer.setVolume(0, 0);

        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnSeekCompleteListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnVideoSizeChangedListener(this);


        if (mAudioSessionId != 0 && mAudioAttributes != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMediaPlayer.setAudioSessionId(mAudioSessionId);
            mMediaPlayer.setAudioAttributes(mAudioAttributes);
        }

        if (mHeaders != null) {
            try {
                mMediaPlayer.setDataSource(getContext(), mUri, mHeaders);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged:" +
                "surface size:" + width + "/" + height +
                "--->parent frame size:" + ((LinearLayout) getParent()).getWidth() + "/" + ((LinearLayout) getParent()).getHeight() +
                "--->surface is creating:" + surfaceHolder.isCreating()
        );
        surfaceWidth = width;
        surfaceHeight = height;


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed:" +
                "surface is creating:" + surfaceHolder.isCreating()
        );
        mSurfaceHolder = null;
        release(true);
    }

    // media play start:
    /*
     log:
        onVideoSizeChanged:width:1280_height:720
        onBufferingUpdate:percent:100
        onPrepared
        handleMessage:curthread:Thread[ChaplinThread,5,main]
        onBufferingUpdate:percent:0
        onInfo：what:703_extra:0
        onInfo：what:701_extra:0
        onVideoSizeChanged:width:1280_height:720
        onBufferingUpdate:percent:9
        onBufferingUpdate:percent:20
        onBufferingUpdate:percent:31
        onInfo：what:703_extra:0
        onInfo：what:702_extra:0
        onBufferingUpdate:percent:31
        onInfo：what:3_extra:0
        onBufferingUpdate:percent:43
        onBufferingUpdate:percent:54
        onBufferingUpdate:percent:65
        onBufferingUpdate:percent:77
        onBufferingUpdate:percent:86
        onBufferingUpdate:percent:97
        onBufferingUpdate:percent:100
        onCompletion
     */
    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.d(TAG, "onVideoSizeChanged:" + width + "/" + height +
                "--->video size:" + mp.getVideoWidth() + "/" + mp.getVideoHeight()
        );
        videoWidth = width;
        videoHeight = height;
        //surface 面积越大，播放视频的性能越好
        if (videoWidth != 0 && videoHeight != 0) {
            if (mSurfaceHolder != null) {
                mSurfaceHolder.setFixedSize(videoWidth, videoHeight);
            }
        }
        if (mOnMediaPlayerListener != null) {
            mOnMediaPlayerListener.onVideoSizeChanged(mp, width, height);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//        Log.d(TAG, "onBufferingUpdate:percent:" + percent);
        if (mOnMediaPlayerListener != null) {
            mOnMediaPlayerListener.onBufferingUpdate(mp, percent);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "onPrepared");
        if (mOnMediaPlayerListener != null) {
            mOnMediaPlayerListener.onPrepared(mp);
        }
        mCurState = State.PREPARED;
        if (hasStickyMessage) {
            hasStickyMessage = false;
            start();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        Log.d(TAG, "onSeekComplete:");
        if (mOnMediaPlayerListener != null) {
            mOnMediaPlayerListener.onSeekComplete(mp);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion:");
        if (mOnMediaPlayerListener != null) {
            mOnMediaPlayerListener.onCompletion(mp);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onError：what:" + what + "--->extra:" + extra);
        return mLogListener != null && mLogListener.onError(mp, what, extra);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onInfo：what:" + what + "--->extra:" + extra);
        return mLogListener != null && mLogListener.onInfo(mp, what, extra);
    }

    // media play end:


    private void release(boolean cleatTargetState) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mCurState = State.IDLE;
            if (cleatTargetState) {
                hasStickyMessage = false;
            }

        }
    }

    //<editor-fold desc="开放的接口，API">
    public boolean isInPlaybackState() {
        return (mMediaPlayer != null &&
                mCurState != State.ERROR &&
                mCurState != State.IDLE &&
                mCurState != State.PREPARING);
    }


    public boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }

    @AnyThread
    public void start() {
        Log.d(TAG, "start:" +
                "--->isInPlaybackState:" + isInPlaybackState()
        );
        if (mMediaPlayer != null && isInPlaybackState()) {
            mMediaPlayer.start();
            mCurState = State.PLAYING;
        } else {//save sticky message
            hasStickyMessage = true;
        }
    }

    @AnyThread
    public void pause() {
        Log.d(TAG, "pause:isPlaying:" + isPlaying());
        if (isPlaying()) {
            mMediaPlayer.pause();
            mCurState = State.PAUSED;
        }
    }

    @AnyThread
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    public void setVideoURI(final Uri uri) {
        setVideoURI(uri, null);
    }

    public void setVideoURI(final Uri uri, final Map<String, String> headers) {
//        mMediaPlayerManager.setVideoURI(uri, headers);
        if (uri == null) {
            throw new NullPointerException("uri param can not be null.");
        }
        mUri = uri;
        mHeaders = headers;

    }
    //</editor-fold>


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
