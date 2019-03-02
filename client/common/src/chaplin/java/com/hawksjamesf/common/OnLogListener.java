package com.hawksjamesf.common;

import android.media.MediaPlayer;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/02/2019  Sat
 */
public interface OnLogListener {

    /**
     * int: the type of error that has occurred:
     * MediaPlayer.MEDIA_ERROR_UNKNOWN
     * MediaPlayer.MEDIA_ERROR_SERVER_DIED
     * <p>
     * int: an extra code, specific to the error. Typically implementation dependent.
     * MediaPlayer.MEDIA_ERROR_IO
     * MediaPlayer.MEDIA_ERROR_MALFORMED
     * MediaPlayer.MEDIA_ERROR_UNSUPPORTED
     * MediaPlayer.MEDIA_ERROR_TIMED_OUT
     * MEDIA_ERROR_SYSTEM (-2147483648) - low-level system error.
     */

    boolean onError(MediaPlayer mp, int what, int extra);

    /**
     * int: the type of info or warning.
     * MediaPlayer.MEDIA_INFO_UNKNOWN = 1
     * MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING = 700
     * MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START = 3
     * MediaPlayer.MEDIA_INFO_BUFFERING_START = 701
     * MediaPlayer.MEDIA_INFO_BUFFERING_END = 702
     * MEDIA_INFO_NETWORK_BANDWIDTH (703) - bandwidth information is available (as extra kbps)
     * MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING = 800
     * MediaPlayer.MEDIA_INFO_NOT_SEEKABLE = 801
     * MediaPlayer.MEDIA_INFO_METADATA_UPDATE = 802
     * MediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901
     * MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT = 902
     */
    boolean onInfo(MediaPlayer mp, int what, int extra);
}
