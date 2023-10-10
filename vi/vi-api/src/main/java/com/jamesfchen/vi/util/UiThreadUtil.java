package com.jamesfchen.vi.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

/** Utility for interacting with the UI thread. */
public class UiThreadUtil {

  @Nullable private static Handler sMainHandler;

  /** @return {@code true} if the current thread is the UI thread. */
  public static boolean isOnUiThread() {
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }

  public static void assertOnUiThread() {
    //nothing
  }


  /** Runs the given {@code Runnable} on the UI thread. */
  public static void runOnUiThread(Runnable runnable) {
    runOnUiThread(runnable, 0);
  }

  /** Runs the given {@code Runnable} on the UI thread with the specified delay. */
  public static void runOnUiThread(Runnable runnable, long delayInMs) {
    synchronized (UiThreadUtil.class) {
      if (sMainHandler == null) {
        sMainHandler = new Handler(Looper.getMainLooper());
      }
    }
    sMainHandler.postDelayed(runnable, delayInMs);
  }
}
