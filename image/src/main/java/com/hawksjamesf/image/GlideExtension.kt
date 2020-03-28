package com.hawksjamesf.image

import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.request.BaseRequestOptions

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Mar/28/2020  Sat
 */
object GlideExtension {
    @GlideOption
    fun squareThumb(requestOptions: BaseRequestOptions<*>): BaseRequestOptions<*> {
        return requestOptions.centerCrop()
    }

//    @GlideOption
//    fun squareMiniThumb(requestOptions: BaseRequestOptions<*>): BaseRequestOptions<*> {
//        return requestOptions.centerCrop().override(Api.SQUARE_THUMB_SIZE)
//    }
}
