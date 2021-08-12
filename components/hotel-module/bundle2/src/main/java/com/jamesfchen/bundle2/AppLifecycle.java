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
//        FontRequest fontRequest = new FontRequest(
//                "com.google.android.gms.fonts",
//                "com.google.android.gms",
//                "emoji compat Font Query",
//                R.array.com_google_android_gms_fonts_certs);
////        owner.getLifecycle()
//        ProcessLifecycleOwner.get()
//        EmojiCompat.Config emojiCompatConfig = new FontRequestEmojiCompatConfig((Application)owner, fontRequest)
//                .setReplaceAll(true)
//                .setEmojiSpanIndicatorColor(Color.GREEN)
//                .registerInitCallback(new EmojiCompat.InitCallback() {
//                    @Override
//                    public void onInitialized() {
//                        super.onInitialized();
//                    }
//
//                    @Override
//                    public void onFailed(@Nullable Throwable throwable) {
//                        super.onFailed(throwable);
//                    }
//                })
//                .setEmojiSpanIndicatorEnabled(true);
//        EmojiCompat.Config bundledEmojiCompatConfig = new BundledEmojiCompatConfig((Application)owner);
//        EmojiCompat.init(emojiCompatConfig);
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
