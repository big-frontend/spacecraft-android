package com.jamesfchen.vi.render;

import static com.jamesfchen.vi.render.FpsKt.TAG_FRAME_MONITOR;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.jamesfchen.vi.DebugOverlayController;
import com.jamesfchen.vi.R;
import com.jamesfchen.vi.WindowOverlayCompat;
import com.jamesfchen.vi.util.UiThreadUtil;

import java.util.Locale;

/**
 * View that automatically monitors and displays the current app frame rate. Also logs the current
 * FPS to logcat while active.
 *
 * <p>NB: Requires API 16 for use of FpsDebugFrameCallback.
 */
public class FpsView extends FrameLayout {

  private static final int UPDATE_INTERVAL_MS = 500;

  private final TextView mTextView;
  private final FpsDebugFrameCallback mFrameCallback;
  private final FPSMonitorRunnable mFPSMonitorRunnable;

  private final WindowManager mWindowManager;

  public FpsView(Context context) {
    super(context);
    inflate(context, R.layout.fps_view, this);
    mTextView = (TextView) findViewById(R.id.fps_text);
    mFrameCallback = new FpsDebugFrameCallback(context);
    mFPSMonitorRunnable = new FPSMonitorRunnable();
    setCurrentFPS(0, 0, 0);
    mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    mFrameCallback.reset();
    mFrameCallback.start();
    mFPSMonitorRunnable.start();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mFrameCallback.stop();
    mFPSMonitorRunnable.stop();
  }

  private void setCurrentFPS(
      double currentFPS, int droppedUIFrames, int total4PlusFrameStutters) {
    String fpsString =
        String.format(
            Locale.US,
            "UI: %.1f fps(%dms)\n%d dropped so far\n%d stutters (4+) so far",
            currentFPS, mFrameCallback.getTotalTimeMS(),
            droppedUIFrames,
            total4PlusFrameStutters);
    mTextView.setText(fpsString);
    Log.e(TAG_FRAME_MONITOR, fpsString);
  }

  /** Timer that runs every UPDATE_INTERVAL_MS ms and updates the currently displayed FPS. */
  private class FPSMonitorRunnable implements Runnable {

    private boolean mShouldStop = false;
    private int mTotalFramesDropped = 0;
    private int mTotal4PlusFrameStutters = 0;

    @Override
    public void run() {
      if (mShouldStop) {
        return;
      }
      mTotalFramesDropped += mFrameCallback.getExpectedNumFrames() - mFrameCallback.getNumFrames();
      mTotal4PlusFrameStutters += mFrameCallback.get4PlusFrameStutters();
      setCurrentFPS(
          mFrameCallback.getFPS(),
          mTotalFramesDropped,
          mTotal4PlusFrameStutters);
      mFrameCallback.reset();

      postDelayed(this, UPDATE_INTERVAL_MS);
    }

    public void start() {
      mShouldStop = false;
      post(this);
    }

    public void stop() {
      mShouldStop = true;
    }
  }

  public void setVisible(final boolean fpsDebugViewVisible) {
    UiThreadUtil.runOnUiThread(
            new Runnable() {
              @Override
              public void run() {
                if (fpsDebugViewVisible) {
                  if (!DebugOverlayController.permissionCheck(getContext())) {
                    Log.d(TAG_FRAME_MONITOR, "Wait for overlay permission to be set");
                    return;
                  }
                  FpsView fpsView = new FpsView(getContext());
                  WindowManager.LayoutParams params =
                          new WindowManager.LayoutParams(
                                  WindowManager.LayoutParams.WRAP_CONTENT,
                                  WindowManager.LayoutParams.WRAP_CONTENT,
                                  WindowOverlayCompat.TYPE_SYSTEM_OVERLAY,
                                  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                          | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                  PixelFormat.TRANSLUCENT);
                  params.gravity = Gravity.TOP | Gravity.RIGHT;
                  mWindowManager.addView(fpsView, params);
                } else if (!fpsDebugViewVisible) {
                  removeAllViews();
                  mWindowManager.removeView(FpsView.this);
                }
              }
            });
  }
}
