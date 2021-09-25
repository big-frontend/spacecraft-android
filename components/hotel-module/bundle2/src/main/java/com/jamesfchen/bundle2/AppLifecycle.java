package com.jamesfchen.bundle2;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.provider.FontRequest;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

@com.jamesfchen.lifecycle.AppLifecycle
public class AppLifecycle implements DefaultLifecycleObserver {
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.d("cjf","bundle2 app onCreate");

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.d("cjf","bundle2 app onStart");

    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.d("cjf","bundle2 app onResume");

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.d("cjf","bundle2 app onPause");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.d("cjf","bundle2 app onStop");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.d("cjf","bundle2 app onDestroy");
    }
}
