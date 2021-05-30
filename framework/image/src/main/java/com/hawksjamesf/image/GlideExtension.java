package com.hawksjamesf.image;

import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.BaseRequestOptions;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Mar/28/2020  Sat
 */
class GlideExtension {
    @GlideOption
      BaseRequestOptions<?> squareThumb(BaseRequestOptions<?> requestOptions){
        return requestOptions.centerCrop();
    }
}
