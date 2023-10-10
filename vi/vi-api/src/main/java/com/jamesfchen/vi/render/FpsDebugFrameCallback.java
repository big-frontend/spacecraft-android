package com.jamesfchen.vi.render;

import static com.jamesfchen.vi.render.FpsKt.TAG_FRAME_MONITOR;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.jamesfchen.vi.util.UiThreadUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * Each time a frame is drawn, records whether it should have expected any more callbacks since the
 * last time a frame was drawn (i.e. was a frame skipped?). Uses this plus total elapsed time to
 * determine FPS. Can also record total and expected frame counts, though NB, since the expected
 * frame rate is estimated, the expected frame count will lose accuracy over time.
 *
 * <p>Also records the JS FPS, i.e. the frames per second with which either JS updated the UI or was
 * idle and not trying to update the UI. This is different from the FPS above since JS rendering is
 * async.
 */
public class FpsDebugFrameCallback extends ChoreographerCompat.FrameCallback {

  public static class FpsInfo {

    public final int totalFrames;
    public final int totalExpectedFrames;
    public final int total4PlusFrameStutters;
    public final double fps;
    public final int totalTimeMs;

    public FpsInfo(
        int totalFrames,
        int totalExpectedFrames,
        int total4PlusFrameStutters,
        double fps,
        int totalTimeMs) {
      this.totalFrames = totalFrames;
      this.totalExpectedFrames = totalExpectedFrames;
      this.total4PlusFrameStutters = total4PlusFrameStutters;
      this.fps = fps;
      this.totalTimeMs = totalTimeMs;
    }
  }

  private static final double EXPECTED_FRAME_TIME = 16.9;

  private @Nullable ChoreographerCompat mChoreographer;
  private final Context mContext;
  private boolean mShouldStop = false;
  private long mFirstFrameTime = -1;
  private long mLastFrameTime = -1;
  private int mNumFrameCallbacks = 0;
  private int mExpectedNumFramesPrev = 0;
  private int m4PlusFrameStutters = 0;
  private boolean mIsRecordingFpsInfoAtEachFrame = false;
  private @Nullable TreeMap<Long, FpsInfo> mTimeToFps;

  public FpsDebugFrameCallback(Context context) {
    mContext = context;
  }

  @Override
  public void doFrame(long l) {
    if (mShouldStop) {
      return;
    }

    if (mFirstFrameTime == -1) {
      mFirstFrameTime = l;
    }

    mLastFrameTime = l;

    mNumFrameCallbacks++;
    int expectedNumFrames = getExpectedNumFrames();
    int framesDropped = expectedNumFrames - mExpectedNumFramesPrev - 1;
    if (framesDropped >= 4) {
      m4PlusFrameStutters++;
    }

    if (mIsRecordingFpsInfoAtEachFrame) {
      FpsInfo info =
          new FpsInfo(
              getNumFrames(),
              expectedNumFrames,
              m4PlusFrameStutters,
              getFPS(),
              getTotalTimeMS());
      mTimeToFps.put(System.currentTimeMillis(), info);
    }
    mExpectedNumFramesPrev = expectedNumFrames;
    if (mChoreographer != null) {
      mChoreographer.postFrameCallback(this);
    }
  }

  public void start() {
    mShouldStop = false;
    final FpsDebugFrameCallback fpsDebugFrameCallback = this;
    UiThreadUtil.runOnUiThread(
        new Runnable() {
          @Override
          public void run() {
            mChoreographer = ChoreographerCompat.getInstance();
            mChoreographer.postFrameCallback(fpsDebugFrameCallback);
          }
        });
  }

  public void startAndRecordFpsAtEachFrame() {
    mTimeToFps = new TreeMap<Long, FpsInfo>();
    mIsRecordingFpsInfoAtEachFrame = true;
    start();
  }

  public void stop() {
    mShouldStop = true;
  }

  public double getFPS() {
    if (mLastFrameTime == mFirstFrameTime) {
      return 0;
    }
    return ((double) (getNumFrames()) * 1e9) / (mLastFrameTime - mFirstFrameTime);
  }

  public int getNumFrames() {
    return mNumFrameCallbacks - 1;
  }

  public int getExpectedNumFrames() {
    double totalTimeMS = getTotalTimeMS();
    int expectedFrames = (int) (totalTimeMS / EXPECTED_FRAME_TIME + 1);
    return expectedFrames;
  }

  public int get4PlusFrameStutters() {
    return m4PlusFrameStutters;
  }

  public int getTotalTimeMS() {
    return (int) ((double) mLastFrameTime - mFirstFrameTime) / 1000000;
  }

  /**
   * Returns the FpsInfo as if stop had been called at the given upToTimeMs. Only valid if
   * monitoring was started with {@link #startAndRecordFpsAtEachFrame()}.
   */
  public @Nullable FpsInfo getFpsInfo(long upToTimeMs) {
    if (mTimeToFps == null){
      Log.e(TAG_FRAME_MONITOR,"FPS was not recorded at each frame!");
    }
    Map.Entry<Long, FpsInfo> bestEntry = mTimeToFps.floorEntry(upToTimeMs);
    if (bestEntry == null) {
      return null;
    }
    return bestEntry.getValue();
  }

  public void reset() {
    mFirstFrameTime = -1;
    mLastFrameTime = -1;
    mNumFrameCallbacks = 0;
    m4PlusFrameStutters = 0;
    mIsRecordingFpsInfoAtEachFrame = false;
    mTimeToFps = null;
  }
}
