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

package com.electrolytej.bundle2.network

import android.net.Uri
import com.electrolytej.bundle2.network.api.ImageApi
import com.electrolytej.bundle2.network.model.PostImageResponse
import com.electrolytej.network.Retrofiter
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import java.io.File

object ImgRetrofiter {
    private val MEDIA_TYPE_PNG = "image/png".toMediaTypeOrNull()

    @JvmStatic
    fun <T> createApi(service: Class<T>): T = Retrofiter.create(
        "https://api.imgur.com/3/", AuthInterceptor()
    ).createApi(service)

    fun uploadImage(imageUri: Uri): Call<PostImageResponse> {
        val imageFile = File(imageUri.path!!)
        val requestFile = RequestBody.create(MEDIA_TYPE_PNG, imageFile)
        val body = MultipartBody.Part.createFormData("image", "image.png", requestFile)
        return createApi(ImageApi::class.java).postImage(body)
    }


}
