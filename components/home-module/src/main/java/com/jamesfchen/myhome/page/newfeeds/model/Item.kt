package com.jamesfchen.myhome.page.newfeeds.model

import android.net.Uri

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jun/07/2020  Sun
 */
data class Item(
    val text: String? = null,
    val photoList: List<Uri>? = null,
//        val thumbnailUriList: List<Uri>
)