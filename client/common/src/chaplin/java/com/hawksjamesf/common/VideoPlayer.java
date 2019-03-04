package com.hawksjamesf.common;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.LinearLayout;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

import androidx.annotation.AnyThread;
import androidx.annotation.RawRes;

/**
 * Copyright ® 2019
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/02/2019  Sat
 */
public class VideoPlayer implements MediaPlayer.OnVideoSizeChangedListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener,
        TextureView.SurfaceTextureListener, SurfaceHolder.Callback {
    public static final String TAG = "Chaplin/PlayerManager";

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

    private OnLogListener mLogListener;

    public void setOnLogListener(OnLogListener logListener) {
        this.mLogListener = logListener;
    }

    private OnMediaPlayerListener mOnMediaPlayerListener;

    public void setOnMediaPlayerListener(OnMediaPlayerListener listener) {
        mOnMediaPlayerListener = listener;

    }

    private Context mContext;
    private TextureView mTextureView;
    private SurfaceTexture mSurfaceTexture;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    public VideoPlayer(Context context) {
        mContext = context;
//        release(false);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setScreenOnWhilePlaying(true);
        mMediaPlayer.setVolume(0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAudioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .build();
            mMediaPlayer.setAudioAttributes(mAudioAttributes);
        }
//        mMediaPlayer.setAudioSessionId(mAudioSessionId);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnSeekCompleteListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnVideoSizeChangedListener(this);
    }


    //TextureView start
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Log.d(TAG, "onSurfaceTextureAvailable:" + width + "/" + height);
        mSurfaceTexture = surfaceTexture;
        mMediaPlayer.setSurface(new Surface(mSurfaceTexture));
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        Log.d(TAG, "onSurfaceTextureSizeChanged surfaceTexture size:" + width + "/" + height +
                "--->parent frame size:" + ((LinearLayout) mTextureView.getParent()).getWidth() + "/" + ((LinearLayout) mTextureView.getParent()).getHeight());

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d(TAG, "onSurfaceTextureDestroyed");
        mSurfaceTexture = null;
        mMediaPlayer.setSurface(null);
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//        Log.d(TAG, "onSurfaceTextureUpdated");

    }
    //TextureView end

    //SurfaceView start
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
        mMediaPlayer.setDisplay(mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = null;
        mMediaPlayer.setDisplay(null);

    }

    //SurfaceView end

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
            if (mSurfaceTexture != null) {
                mSurfaceTexture.setDefaultBufferSize(videoWidth, videoHeight);
            }

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
        Log.d(TAG, "onBufferingUpdate:percent:" + percent);
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

    public void setDataSource(final Uri uri) {
        setDataSource(uri, null);
    }

    public void setDataSource(final Uri uri, final Map<String, String> headers) {
        mUri = uri;
        mHeaders = headers;
        try {
            mMediaPlayer.setDataSource(mContext, mUri, mHeaders);
            mMediaPlayer.prepareAsync();
            mCurState = State.PREPARING;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDataSource(@RawRes int resid) {
        AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(resid);
        if (afd == null) return;
        try {
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mMediaPlayer.prepareAsync();
            mCurState = State.PREPARING;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDataSource(FileDescriptor fd, long offset, long length) {
        try {
            mMediaPlayer.setDataSource(fd, offset, length);
            mMediaPlayer.prepareAsync();
            mCurState = State.PREPARING;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void bindTextureView(TextureView textureView) {
        mTextureView = textureView;
        mTextureView.setSurfaceTextureListener(this);
    }

    public void bindSurfaceView(SurfaceView surfaceView) {
        mSurfaceView = surfaceView;
        mSurfaceView.getHolder().addCallback(this);
    }

    public static VideoPlayer createAndBind(Context context, TextureView textureView) {
        VideoPlayer videoPlayer = new VideoPlayer(context);
        videoPlayer.bindTextureView(textureView);
        return videoPlayer;
    }

    public static VideoPlayer createAndBind(Context context, TextureView textureView, int resid) {
        try {
            VideoPlayer videoPlayer = new VideoPlayer(context);
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(resid);
            if (afd == null) return null;
            videoPlayer.bindTextureView(textureView);
            videoPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            return videoPlayer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static VideoPlayer createAndBind(Context context, SurfaceView surfaceView) {
        VideoPlayer videoPlayer = new VideoPlayer(context);
        videoPlayer.bindSurfaceView(surfaceView);
        return videoPlayer;
    }

    public static VideoPlayer createAndBind(Context context, SurfaceView surfaceView, @RawRes int resid) {
        try {
            VideoPlayer videoPlayer = new VideoPlayer(context);
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(resid);
            if (afd == null) return null;
            videoPlayer.bindSurfaceView(surfaceView);
            videoPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            return videoPlayer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param context
     * @param resid   R.raw.xxx
     * @return
     */
    public static VideoPlayer create(Context context, @RawRes int resid) {
        try {
            VideoPlayer videoPlayer = new VideoPlayer(context);
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(resid);
            if (afd == null) return null;
            videoPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            return videoPlayer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static VideoPlayer create(Context context, Uri uri) {
        VideoPlayer videoPlayer = new VideoPlayer(context);
        videoPlayer.setDataSource(uri);
        return videoPlayer;
    }
    //</editor-fold>
}
