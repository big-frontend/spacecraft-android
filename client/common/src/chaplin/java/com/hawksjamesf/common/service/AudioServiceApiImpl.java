package com.hawksjamesf.common.service;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.hawksjamesf.common.IAudioServiceApi;

import java.io.IOException;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/02/2019  Sat
 */
public class AudioServiceApiImpl extends IAudioServiceApi.Stub implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {

    public static final String TAG = "audio/Binder";
    private Uri mUri;
    private MediaPlayer mMediaPlayer;
    private Context mContext;

    AudioServiceApiImpl(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnSeekCompleteListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnInfoListener(this);

        mMediaPlayer.setScreenOnWhilePlaying(true);
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void setUriPath(String path) throws RemoteException {
        mUri = Uri.parse(path);
        try {
            mMediaPlayer.setDataSource(mContext, mUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start() throws RemoteException {

    }

    @Override
    public void pause() throws RemoteException {

    }

    @Override
    public void stop() throws RemoteException {

    }

    //media player
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d(TAG, "onBufferingUpdate:percent:" + percent);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "onPrepared");
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        Log.d(TAG, "onSeekComplete");

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion");

    }
     /*
    	int: the type of error that has occurred:
            MediaPlayer.MEDIA_ERROR_UNKNOWN
            MediaPlayer.MEDIA_ERROR_SERVER_DIED

        int: an extra code, specific to the error. Typically implementation dependent.
            MediaPlayer.MEDIA_ERROR_IO
            MediaPlayer.MEDIA_ERROR_MALFORMED
            MediaPlayer.MEDIA_ERROR_UNSUPPORTED
            MediaPlayer.MEDIA_ERROR_TIMED_OUT
            MEDIA_ERROR_SYSTEM (-2147483648) - low-level system error.
     */

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onError：what:" + what + "_extra:" + extra);
        return false;
    }

    /*
    	int: the type of info or warning.
                MediaPlayer.MEDIA_INFO_UNKNOWN = 1
                MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING = 700
                MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START = 3
                MediaPlayer.MEDIA_INFO_BUFFERING_START = 701
                MediaPlayer.MEDIA_INFO_BUFFERING_END = 702
                MEDIA_INFO_NETWORK_BANDWIDTH (703) - bandwidth information is available (as extra kbps)
                MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING = 800
                MediaPlayer.MEDIA_INFO_NOT_SEEKABLE = 801
                MediaPlayer.MEDIA_INFO_METADATA_UPDATE = 802
                MediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901
                MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT = 902
     */
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onInfo：what:" + what + "_extra:" + extra);
        return false;
    }
    //media player
}
