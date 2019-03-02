package com.hawksjamesf.common;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.FrameLayout;

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
public class VideoTextureView extends TextureView implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnCompletionListener {
    public static final String TAG = "Chaplin/SurfaceTexture";
    public static final String videoUrl = "https://video.c-ctrip.com/videos/u0030l000000dbzlh5934.mp4";
    public static final String gifUrl = "https://n.sinaimg.cn/tech/transform/138/w600h338/20190228/VRRf-htptaqf5558611.gif";

    public int surfaceWidth;
    public int surfaceHeight;
    public int videoWidth;
    public int videoHeight;

    public boolean hasStickyMessage = false;
    private State mCurState = State.IDLE;
    private Uri mUri = Uri.parse(videoUrl);
    private MediaPlayer mMediaPlayer;
    private int mAudioSession;
    private AudioAttributes mAudioAttributes;
    private Map<String, String> mHeaders;


    public VideoTextureView(Context context) {
        super(context);
        initView(null, 0);
    }

    public VideoTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs, 0);
    }

    public VideoTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VideoTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(AttributeSet attributeSet, int defStyleAttr) {
        setSurfaceTextureListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
//        mTextureView.setAlpha(1.0f);
//        mTextureView.setRotation(90.0f);
//        mTextureView.setTransform();
    }

    //surface
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Log.d(TAG, "onSurfaceTextureAvailable:" + width + "/" + height);
        if (mUri == null) {
            return;
        }

        release(false);

        try {
            //setAudioAttributes->setAudioSessionId->setDataSource->setDisplay->prepare
            mMediaPlayer = MediaPlayer.create(getContext(), mUri);
            mMediaPlayer.setSurface(new Surface(surfaceTexture));
            mCurState = State.PREPARING;
            if (mAudioSession != 0 && mAudioAttributes != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            } else {
            }

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnSeekCompleteListener(this);
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
//            mMediaPlayer.setOnErrorListener(this);
//            mMediaPlayer.setOnInfoListener(this);

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
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        Log.d(TAG, "onSurfaceTextureSizeChanged:" + width + "/" + height +
                "--->parent frame size:" + ((FrameLayout) getParent()).getWidth() + "/" + ((FrameLayout) getParent()).getHeight());

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d(TAG, "onSurfaceTextureDestroyed");
        return false;
    }


    /**
     * 这个回调是实时的，而非用Camera的PreviewCallback这种2次回调的方式。从时间看，基本上每32ms左右上来一帧数据，即每秒30帧，跟本手机的Camera的性能吻合
     *
     * @param surfaceTexture
     */
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
//        Log.d(TAG, "onSurfaceTextureUpdated");

    }
    //surface


    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.d(TAG, "onVideoSizeChanged:" + width + "/" + height +
                "--->video size:" + mp.getVideoWidth() + "/" + mp.getVideoHeight() +
                "--->parent frame size:" + px2dp(((FrameLayout) getParent()).getWidth()) + "/" + px2dp(((FrameLayout) getParent()).getHeight())
        );
        videoWidth = mp.getVideoWidth();
        videoHeight = mp.getVideoHeight();
    }


    public int px2dp(final float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//        Log.d(TAG, "onBufferingUpdate:percent:" + percent);
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

    private boolean isInPlaybackState() {
        return (mMediaPlayer != null &&
                mCurState != State.ERROR &&
                mCurState != State.IDLE &&
                mCurState != State.PREPARING);
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
    //</editor-fold>
}
