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

    //    public int width;
//    public int height;
    //    public int surfaceWidth;
//    public int surfaceHeight;
    private State mCurState = State.IDLE;
    private State mTargetState = State.IDLE;
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

    private VideoPlayer(Context context) {
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


    /**
     * TextureView start
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Log.d(TAG, "onSurfaceTextureAvailable:" + width + "/" + height);
        mSurfaceTexture = surfaceTexture;
        mMediaPlayer.setSurface(new Surface(mSurfaceTexture));
    }

    //修改LayouParament才会调用这个方法
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        Log.d(TAG, "onSurfaceTextureSizeChanged surfaceTexture size:" + width + "/" + height
                + "--->parent frame size:" + ((LinearLayout) mTextureView.getParent()).getWidth() + "/" + ((LinearLayout) mTextureView.getParent()).getHeight()
        );
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
//        Log.d(TAG, "onSurfaceTextureDestroyed");
        mSurfaceTexture = null;
        mMediaPlayer.setSurface(null);
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//        Log.d(TAG, "onSurfaceTextureUpdated");

    }
    /**
     * TextureView end
     */

    /**
     * SurfaceView start
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated");
        mSurfaceHolder = surfaceHolder;
        mMediaPlayer.setDisplay(mSurfaceHolder);
    }

    /* SurfaceHolder#setFixedSize方法会触发该方法调用
    format:PixelFormat
    public static final int RGBA_8888    = 1;
    public static final int RGBX_8888    = 2;
    public static final int RGB_888      = 3;
    public static final int RGB_565      = 4;
    public static final int RGBA_F16     = 0x16;
    public static final int RGBA_1010102 = 0x2B;
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged format:" + format +
                "--->surface size:" + width + "/" + height
                + "--->parent frame size:" + ((LinearLayout) mSurfaceView.getParent()).getWidth() + "/" + ((LinearLayout) mSurfaceView.getParent()).getHeight()
        );

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//        Log.d(TAG, "surfaceDestroyed");
        mSurfaceHolder = null;
        mMediaPlayer.setDisplay(null);

    }

    /**
     * SurfaceView end
     */

    /**
     * media play start:
     * log:
     * onVideoSizeChanged:width:1280_height:720
     * onBufferingUpdate:percent:100
     * onPrepared
     * handleMessage:curthread:Thread[ChaplinThread,5,main]
     * onBufferingUpdate:percent:0
     * onInfo：what:703_extra:0
     * onInfo：what:701_extra:0
     * onVideoSizeChanged:width:1280_height:720
     * onBufferingUpdate:percent:9
     * onBufferingUpdate:percent:20
     * onBufferingUpdate:percent:31
     * onInfo：what:703_extra:0
     * onInfo：what:702_extra:0
     * onBufferingUpdate:percent:31
     * onInfo：what:3_extra:0
     * onBufferingUpdate:percent:43
     * onBufferingUpdate:percent:54
     * onBufferingUpdate:percent:65
     * onBufferingUpdate:percent:77
     * onBufferingUpdate:percent:86
     * onBufferingUpdate:percent:97
     * onBufferingUpdate:percent:100
     * onCompletion
     */
    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.d(TAG, "onVideoSizeChanged:" + width + "/" + height +
                "--->video size:" + mp.getVideoWidth() + "/" + mp.getVideoHeight()
        );
        //surface 面积越大，播放视频的性能越好
        if (width != 0 && height != 0) {
            if (mSurfaceTexture != null) {
                mSurfaceTexture.setDefaultBufferSize(width, height);
            }

            if (mSurfaceHolder != null) {
                mSurfaceHolder.setFixedSize(width, height);

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
        if (mTargetState == State.PLAYING) {
            if (mSurfaceHolder != null && !mSurfaceHolder.isCreating()) {
                start();
            }

            if (mTextureView != null && mTextureView.isAvailable()) {
                start();
            }
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
        String whatdesc = "";
        switch (what) {
            case 1: {
                whatdesc = "MEDIA_ERROR_UNKNOWN";
                break;
            }
            case 100: {
                whatdesc = "MEDIA_ERROR_SERVER_DIED";
                break;
            }
        }
        String extradesc = "";
        switch (extra) {
            case -110: {

                extradesc = "MEDIA_ERROR_TIMED_OUT";
                break;
            }
            case -1004: {

                extradesc = "MEDIA_ERROR_IO";
                break;
            }
            case -1007: {

                extradesc = "MEDIA_ERROR_MALFORMED";
                break;
            }
            case -1010: {

                extradesc = "MEDIA_ERROR_UNSUPPORTED";
                break;
            }
            case -2147483648: {

                extradesc = "MEDIA_ERROR_SYSTEM";
                break;
            }
        }
        Log.d(TAG, "onError：what/desc:" + what + "/" + whatdesc + "--->extra/desc:" + extra + "/" + extradesc);
        return mLogListener != null && mLogListener.onError(mp, what, extra);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        String text = "";
        switch (what) {
            case 1: {
                text = "MEDIA_INFO_UNKNOWN";
                break;
            }
            case 2: {
                //The player was started because it was used as the next player for another player, which just completed playback
                //setNextMediaPlayer(MediaPlayer)
                text = "MEDIA_INFO_STARTED_AS_NEXT";
                break;
            }
            case 3: {
                text = "MEDIA_INFO_VIDEO_RENDERING_START";//渲染第一帧
                break;
            }
            case 700: {
                //The video is too complex for the decoder: it can't decode frames fast enough. Possibly only the audio plays fine at this stage.
                text = "MEDIA_INFO_VIDEO_TRACK_LAGGING";
                break;
            }
            case 701: {
                text = "MEDIA_INFO_BUFFERING_START";
                break;
            }
            case 702: {
                text = "MEDIA_INFO_BUFFERING_END";
                break;
            }
            case 703: {
                text = "MEDIA_INFO_NETWORK_BANDWIDTH";//网络宽带
                break;
            }
            case 800: {
                //Bad interleaving means that a media has been improperly interleaved or not interleaved at all,
                // e.g has all the video samples first then all the audio ones. Video is playing but a lot of disk seeks may be happening
                text = "MEDIA_INFO_BAD_INTERLEAVING";//正常情况下音频和视频样本将依序排列，交错情况下音频和视频数据会不正确
                break;
            }
            case 801: {
                text = "MEDIA_INFO_NOT_SEEKABLE";//不能seek，可能是一个直播流
                break;
            }
            case 802: {
                text = "MEDIA_INFO_METADATA_UPDATE";//metadata更新
//                mp.getMetadata
                break;
            }
            case 804: {
                text = "MEDIA_INFO_AUDIO_NOT_PLAYING";
                break;
            }
            case 805: {
                text = "MEDIA_INFO_VIDEO_NOT_PLAYING";
                break;
            }
            case 901: {
                //Subtitle track was not supported by the media framework.
                text = "MEDIA_INFO_UNSUPPORTED_SUBTITLE";
                break;
            }
            case 902: {
                //Reading the subtitle track takes too long.
                text = "MEDIA_INFO_SUBTITLE_TIMED_OUT";
                break;
            }
        }
        Log.d(TAG, "onInfo：what/desc:" + what + "/" + text + "--->extra:" + extra);
        return mLogListener != null && mLogListener.onInfo(mp, what, extra);
    }

    /**
     * media play end:
     */

    private void bindTextureView(TextureView textureView) {
        mTextureView = textureView;
        mTextureView.setSurfaceTextureListener(this);
    }

    private void bindSurfaceView(SurfaceView surfaceView) {
        mSurfaceView = surfaceView;
        mSurfaceView.getHolder().addCallback(this);
    }

    private void release(boolean cleatTargetState) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mCurState = State.IDLE;
            if (cleatTargetState) {
                mTargetState = State.IDLE;
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
        }
        mTargetState = State.PLAYING;
    }

    public void resume() {

    }

    @AnyThread
    public void pause() {
        Log.d(TAG, "pause:isPlaying:" + isPlaying());
        if (isInPlaybackState() && isPlaying()) {
            mMediaPlayer.pause();
            mCurState = State.PAUSED;
        }
        mTargetState = State.PAUSED;
    }

    @AnyThread
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }


    public int getDuration() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getDuration();
        } else {

            return -1;
        }
    }

    public int getCurrentPositiono() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getCurrentPosition();
        } else {
            return -1;
        }
    }


    public void setDataSource(final Uri uri) {
        setDataSource(uri, null);
    }

    // For streams, you should call prepareAsync(), which returns immediately, rather than blocking until enough data has been buffered.
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
        setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
    }

    //For files, it is OK to call prepare(), which blocks until MediaPlayer is ready for playback
    public void setDataSource(FileDescriptor fd, long offset, long length) {
        try {
            mMediaPlayer.setDataSource(fd, offset, length);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    public void setDataSourceAndPlay(final Uri uri) {
//        setDataSourceAndPlay(uri, null);
//
//    }
//
//    public void setDataSourceAndPlay(final Uri uri, final Map<String, String> headers) {
//        setDataSource(uri, headers);
//        start();
//    }
//
//    public void setDataSourceAndPlay(@RawRes int resid) {
//        setDataSource(resid);
//        start();
//    }
//
//    public void setDataSourceAndPlay(FileDescriptor fd, long offset, long length) {
//        setDataSource(fd, offset, length);
//        start();
//    }

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
