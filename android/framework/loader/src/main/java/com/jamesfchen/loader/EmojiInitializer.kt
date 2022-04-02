package com.jamesfchen.loader

import android.content.Context
import android.graphics.Color
import com.jamesfchen.loader.R
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import androidx.emoji.text.EmojiCompat.InitCallback
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.startup.Initializer

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Sep/25/2021  Sat
 */
class EmojiInitializer : Initializer<Unit> {
    override fun create(context: Context) {
//        val fontRequest = FontRequest(
//            "com.google.android.gms.fonts",
//            "com.google.android.gms",
//            "emoji compat Font Query",
//            R.array.com_google_android_gms_fonts_certs
//        )
//        val emojiCompatConfig = FontRequestEmojiCompatConfig(context, fontRequest)
//            .setReplaceAll(true)
//            .setEmojiSpanIndicatorColor(Color.GREEN)
//            .registerInitCallback(object : InitCallback() {
//                override fun onInitialized() {
//                    super.onInitialized()
//                }
//
//                override fun onFailed(@Nullable throwable: Throwable?) {
//                    super.onFailed(throwable)
//                }
//            })
//            .setEmojiSpanIndicatorEnabled(true)
        val bundledEmojiCompatConfig: EmojiCompat.Config = BundledEmojiCompatConfig(context)
        EmojiCompat.init(bundledEmojiCompatConfig)
    }

    override fun dependencies(): List<Class<out Initializer<*>?>> {
        return emptyList()
    }
}