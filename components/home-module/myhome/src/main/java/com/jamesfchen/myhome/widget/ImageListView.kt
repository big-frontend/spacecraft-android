package com.jamesfchen.myhome.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jamesfchen.common.util.ConvertUtil.dp2px
import com.jamesfchen.loader.G
import com.jamesfchen.loader.GlideRequests

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Sep/25/2021  Sat
 */
class ImageListView : LinearLayout {
    private var mUriList: List<Uri>? = null
    private var mOnClick: OnClick? = null
    var mRequestBuilder: GlideRequests = G.with(context)
    val glideDrawableRequest = mRequestBuilder?.asDrawable()
    val gldeGifRequest = mRequestBuilder?.asGif()
    val gldeBitmapRequest = mRequestBuilder?.asBitmap()
    val l1 = LinearLayout(context)
    val l2 = LinearLayout(context)
    val l3 = LinearLayout(context)
    init {
        orientation = LinearLayout.VERTICAL
        l1.orientation = HORIZONTAL
        l2.orientation = HORIZONTAL
        l3.orientation = HORIZONTAL
    }

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setDataList(uriList: List<Uri>?) {
        Log.d("imagelistview", "setDataList0 size:${uriList?.size ?: 0}")
        removeAllViews()
        l1.removeAllViews()
        l2.removeAllViews()
        l3.removeAllViews()
        if (uriList.isNullOrEmpty()) {
            mUriList = null
            return
        }
        mUriList = uriList
        for (i in 0 until uriList.size) {
            //一张有两种尺寸，让服务端下发的url带有宽高
            // 2-9张都是固定尺寸，排版有点不同
            val img = ImageView(context)
            val uri = uriList[i]
            var width = uri.getQueryParameter("w")?.toInt() ?: 0
            var height = uri.getQueryParameter("h")?.toInt() ?: 0
            var rawUri = uri.buildUpon().clearQuery().build()
            Log.d("imagelistview", "setDataList1 ${width}/${height} ${rawUri}")
            mRequestBuilder?.load(rawUri)
//                ?.logo()
                ?.into(img)
            if (uriList.size == 1) {
                //按照比例压缩 scale
                if (width > height) {
                    var scale = width / height
                    width = dp2px(150f)
                    height = dp2px(50f)
                } else if (width < height) {
                    width = dp2px(50f)
                    height = dp2px(150f)
                }
                val lp = LayoutParams(width, height)
                img.layoutParams = lp
                img.tag = rawUri
                l1.addView(img, lp)
            } else {
                width = dp2px(70f)
                height = dp2px(70f)
                if (uriList.size < 4) {
                    val lp = LayoutParams(width, height)
                    img.layoutParams = lp
                    img.tag = rawUri
                    l1.addView(img, lp)
                } else if (uriList.size == 4) {
                    val lp = LayoutParams(width, height)
                    img.layoutParams = lp
                    img.tag = rawUri
                    if (i < 2) {
                        l1.addView(img, lp)
                    } else {
                        l2.addView(img, lp)
                    }
                } else {
                    val lp = LayoutParams(width, height)
                    img.layoutParams = lp
                    img.tag = rawUri
                    if (i < 3) {
                        l1.addView(img, lp)
                    } else if (i < 6) {
                        l2.addView(img, lp)
                    } else {
                        l3.addView(img, lp)

                    }
                }
            }
//            attachViewToParent(img, i, lp)
        }
        if (uriList.size < 3) {
            addView(l1)
        } else if (uriList.size < 6) {
            addView(l1)
            addView(l2)
        } else {
            addView(l1)
            addView(l2)
            addView(l3)
        }

    }

    override fun onViewAdded(child: View) {
        super.onViewAdded(child)
    }

    fun setOnItemClickListener(onClick: (view: View, position: Int) -> Unit) {
        mOnClick = onClick
    }

}
typealias OnClick = (view: View, position: Int) -> Unit