package com.electrolytej.av;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/02/2019  Sat
 */
public enum State {
    IDLE,
    INITIALIZED,
    PREPARING,
    PREPARED,//prepared
    PLAYING,//start
//    STARTED,//start
    PAUSED,//pause
    STOPPED,//stop
    PLAYBACK_COMPLETED,
    END,
    ERROR

}
