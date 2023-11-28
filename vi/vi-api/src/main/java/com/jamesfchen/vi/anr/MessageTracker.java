package com.jamesfchen.vi.anr;

import static com.jamesfchen.vi.anr.AnrKt.TAG_ANR_MONITOR;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Printer;

public class MessageTracker implements Printer, Handler.Callback {
    private static final int MSG_P = 1;
    private static final int THRESHOLD = 1001;
    private String mLogMsg;

    private long mMsgDispatchTime = 0; // 事件派发时间
    private HandlerThread mWatchDogThread;

    private Handler mHandler;

    public void start() {
        if (mWatchDogThread == null) {
            mWatchDogThread = new HandlerThread("ANR_WATCH_DOG");
            mWatchDogThread.start();
            mHandler = new Handler(mWatchDogThread.getLooper(), this);
            Looper.getMainLooper().setMessageLogging(this);
        }
    }

    @Override
    public void println(String x) {
        if (x != null) {
            if (x.startsWith(">>>>> Dispatching to")) {
                logBeginDispatchMsg(x);
            } else if (x.startsWith("<<<<< Finished to")) {
                logEndDispatchMsg(x);
            }
        }
    }

    private void logBeginDispatchMsg(String x) {
        mLogMsg = x;
        mMsgDispatchTime = System.currentTimeMillis();
//        mHandler.removeMessages(MSG_P);
//        Message msg = mHandler.obtainMessage(MSG_P);
//        msg.obj = String.format("处理message耗时超过1s ,%s At %s.%d", mLogMsg, DateFormat.format("yyyy-MM-dd kk:mm:ss", mMsgDispatchTime), mMsgDispatchTime % 1000);
//        mHandler.sendMessageDelayed(msg, 1500);
    }

    private void logEndDispatchMsg(String x) {
        if ((System.currentTimeMillis() - mMsgDispatchTime) > 16) {
            long cost = System.currentTimeMillis() - mMsgDispatchTime;
            Log.w(TAG_ANR_MONITOR, "dispatchMessage "+mLogMsg+",cost:"+ cost+"ms");
        }
        if (mMsgDispatchTime > 0 && mLogMsg != null) {
//            mHandler.removeMessages(MSG_P);
            mMsgDispatchTime = 0;
            mLogMsg = null;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.w(TAG_ANR_MONITOR, (String) msg.obj);
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper == null) {
            Log.w(TAG_ANR_MONITOR, "Not Find Main Looper.");
        } else {
            Thread thread = mainLooper.getThread();
            if (thread == null) {
                Log.w(TAG_ANR_MONITOR, "main thread is null.");
            } else {
                StackTraceElement[] stackTrace = thread.getStackTrace();
                StringBuilder sb = new StringBuilder("Call Stack:\n");
                for (StackTraceElement stack : stackTrace) {
                    sb.append("\tat ").append(stack).append("\n");
                }
                Log.w(TAG_ANR_MONITOR, sb.toString());
            }
        }
        return true;
    }
}
