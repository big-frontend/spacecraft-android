package com.jamesfchen.vi.render;

import android.os.Handler;
import android.view.Choreographer;

import com.jamesfchen.vi.util.UiThreadUtil;

public class ChoreographerCompat {

  private static final long ONE_FRAME_MILLIS = 17;
  private static ChoreographerCompat sInstance;

  private Handler mHandler;
  private Choreographer mChoreographer;

  public static ChoreographerCompat getInstance() {
    UiThreadUtil.assertOnUiThread();
    if (sInstance == null) {
      sInstance = new ChoreographerCompat();
    }
    return sInstance;
  }

  private ChoreographerCompat() {
    mChoreographer = getChoreographer();
  }

  public void postFrameCallback(FrameCallback callbackWrapper) {
    choreographerPostFrameCallback(callbackWrapper.getFrameCallback());
  }

  public void postFrameCallbackDelayed(FrameCallback callbackWrapper, long delayMillis) {
    choreographerPostFrameCallbackDelayed(callbackWrapper.getFrameCallback(), delayMillis);
  }

  public void removeFrameCallback(FrameCallback callbackWrapper) {
    choreographerRemoveFrameCallback(callbackWrapper.getFrameCallback());
  }

  private Choreographer getChoreographer() {
    return Choreographer.getInstance();
  }

  private void choreographerPostFrameCallback(Choreographer.FrameCallback frameCallback) {
    mChoreographer.postFrameCallback(frameCallback);
  }

  private void choreographerPostFrameCallbackDelayed(
      Choreographer.FrameCallback frameCallback, long delayMillis) {
    mChoreographer.postFrameCallbackDelayed(frameCallback, delayMillis);
  }

  private void choreographerRemoveFrameCallback(Choreographer.FrameCallback frameCallback) {
    mChoreographer.removeFrameCallback(frameCallback);
  }

  /**
   * This class provides a compatibility wrapper around the JellyBean FrameCallback with methods to
   * access cached wrappers for submitting a real FrameCallback to a Choreographer or a Runnable to
   * a Handler.
   */
  public abstract static class FrameCallback {

    private Runnable mRunnable;
    private Choreographer.FrameCallback mFrameCallback;

    Choreographer.FrameCallback getFrameCallback() {
      if (mFrameCallback == null) {
        mFrameCallback =
            new Choreographer.FrameCallback() {
              @Override
              public void doFrame(long frameTimeNanos) {
                FrameCallback.this.doFrame(frameTimeNanos);
              }
            };
      }
      return mFrameCallback;
    }

    Runnable getRunnable() {
      if (mRunnable == null) {
        mRunnable =
            new Runnable() {
              @Override
              public void run() {
                doFrame(System.nanoTime());
              }
            };
      }
      return mRunnable;
    }

    /**
     * Just a wrapper for frame callback, see {@link
     * Choreographer.FrameCallback#doFrame(long)}.
     */
    public abstract void doFrame(long frameTimeNanos);
  }
}
