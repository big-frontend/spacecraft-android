/*
 *
 *  * Copyright (C) 2018 The Android Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.jamesfchen.myhome.screen.photo.repository

import android.net.Uri
import java.util.*

/**
 * Helps produce a random stock image [Uri].
 */
object StockImages {
    private val sRandom = Random()

    @JvmField
    val uriList = listOf(
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG168.jpeg?alt=media&token=2a28b72e-d8a5-4c0f-b919-853722c850a2"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG173.jpeg?alt=media&token=67c4a264-1ab4-47ca-971c-25e1571ed084"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG175.jpeg?alt=media&token=6596738d-e940-4158-bfa9-2f16d2586865"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG174.jpeg?alt=media&token=331f983e-e69c-4156-ab3b-d82980f6ad91"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"),
            Uri.parse("file:///android_asset/images/lit_pier.jpg"),
            Uri.parse("file:///android_asset/images/parting_ways.jpg"),
            Uri.parse("file:///android_asset/images/wrong_way.jpg"),
            Uri.parse("file:///android_asset/images/jack_beach.jpg"),
            Uri.parse("file:///android_asset/images/webp/jack_blur.webp"),
            Uri.parse("file:///android_asset/images/webp/jack_road.webp"),
            Uri.parse("file:///android_asset/images/webp/wechatimg211.webp"),
            Uri.parse("file:///android_asset/images/wechatimg212.png")
    )

    /**
     * This method produces a random image [Uri]. This is so you can see
     * the effects of applying filters on different kinds of stock images.
     *
     * @return a random stock image [Uri].
     */
    fun randomStockImage(): Uri {
        val index = sRandom.nextInt(uriList.size)
        return uriList[index]
    }
}
