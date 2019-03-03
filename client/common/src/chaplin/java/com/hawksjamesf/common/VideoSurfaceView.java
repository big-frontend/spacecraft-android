package com.hawksjamesf.common;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener/*, Handler.Callback*/ {

    private static final String TAG = "Chaplin/Surface";

    public int surfaceWidth;
    public int surfaceHeight;
    public int videoWidth;
    public int videoHeight;


    private SurfaceHolder mSurfaceHolder;

    public boolean hasStickyMessage = false;
    private State mCurState = State.IDLE;
    private Uri mUri ;
    private Map<String, String> mHeaders;
    private MediaPlayer mMediaPlayer;
    private int mAudioSession;
    private AudioAttributes mAudioAttributes;

    private OnLogListener mLogListener;

    public void setLogistener(OnLogListener logListener) {
        this.mLogListener = logListener;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAudioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .build();
        }

        getHolder().addCallback(this);
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated:" +
                "curthread:" + Thread.currentThread() + "/mainThread:" + Looper.getMainLooper().getThread() +
                "\ncreate surface:" + surfaceHolder.isCreating()
        );
        mSurfaceHolder = surfaceHolder;
        if (mUri == null) {
            return;
        }
        release(false);

        try {
            if (mAudioSession != 0 && mAudioAttributes != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mMediaPlayer = MediaPlayer.create(getContext(), mUri, mSurfaceHolder, mAudioAttributes, mAudioSession);
            } else {
                //setAudioAttributes->setAudioSessionId->setDataSource->setDisplay->prepare
                mMediaPlayer = MediaPlayer.create(getContext(), mUri, mSurfaceHolder);
                mCurState = State.PREPARING;
            }

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnSeekCompleteListener(this);
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setOnInfoListener(this);

            //drm
//        mMediaPlayer.setOnDrmInfoListener(this);
//        mMediaPlayer.setOnDrmConfigHelper(this);
//        mMediaPlayer.setOnDrmPreparedListener(this);

            //video listener
            mMediaPlayer.setOnVideoSizeChangedListener(this);
//        mMediaPlayer.addTimedTextSource();
//        mMediaPlayer.setOnTimedTextListener(this);//字幕
//        mMediaPlayer.setOnSubtitleDataListener(this);
//        mMediaPlayer.setOnMediaTimeDiscontinuityListener(this);
//        mMediaPlayer.setOnTimedMetaDataAvailableListener(this);

            if (mHeaders != null) {
                mMediaPlayer.setDataSource(getContext(), mUri, mHeaders);
            }
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.setVolume(0, 0);
        } catch (IOException e) {
            e.printStackTrace();
            mCurState = State.ERROR;
        } finally {

        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged:" +
                "curthread:" + Thread.currentThread() + "/mainThread:" + Looper.getMainLooper().getThread() +
                "\nsurface width:" + width + "--->surface height:" + height +
                "\ngetWidth():" + getWidth() + "--->getHeight():" + getHeight() +
                "\ncreate surface:" + surfaceHolder.isCreating()
        );
        surfaceWidth = width;
        surfaceHeight = height;


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed:curthread:" + Thread.currentThread() +
                "\ncreate surface:" + surfaceHolder.isCreating()
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
        Log.d(TAG, "onVideoSizeChanged:width:" + mp.getVideoWidth() + "--->height:" + mp.getVideoHeight());
        videoWidth = mp.getVideoWidth();
        videoHeight = mp.getVideoHeight();
        if (videoWidth != 0 && videoHeight != 0) {
            mSurfaceHolder.setFixedSize(videoWidth, videoHeight);
            requestFocus();
        }

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d(TAG, "onBufferingUpdate:percent:" + percent);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "onPrepared");
        mCurState = State.PREPARED;
        if (hasStickyMessage) {
            hasStickyMessage = false;
            start();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        Log.d(TAG, "onSeekComplete");

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion");

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
    // media play end



    private boolean isInPlaybackState() {
        return (mMediaPlayer != null &&
                mCurState != State.ERROR &&
                mCurState != State.IDLE &&
                mCurState != State.PREPARING);
    }

    private boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }

    private void release(boolean cleatTargetState) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurState = State.IDLE;
            if (cleatTargetState) {
                hasStickyMessage = false;
            }

        }
    }

    //<editor-fold desc="开放的接口，API">
    @AnyThread
    public void start() {
        Log.d(TAG, "start:" +
                "isInPlaybackState:" + isInPlaybackState()
        );
        if (isInPlaybackState()) {
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
            mCurState = State.PAUSE;
        }
    }

    @AnyThread
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }


    public boolean isCreatingSurface() {
        return mSurfaceHolder != null && mSurfaceHolder.isCreating();
    }

    //</editor-fold>

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
