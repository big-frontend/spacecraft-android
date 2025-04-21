package com.electrolytej.feeds.widget

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.blankj.utilcode.util.ConvertUtils
import com.bumptech.glide.Glide

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Sep/25/2021  Sat
 */
class PhotoListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
    defStyleRes: Int = -1
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private var mUriList: List<Uri>? = null
    private var mOnClick: OnClick? = null
    var mRequestBuilder = Glide.with(context)
    val glideDrawableRequest = mRequestBuilder.asDrawable()
    val gldeGifRequest = mRequestBuilder.asGif()
    val gldeBitmapRequest = mRequestBuilder.asBitmap()
    private val l1 = LinearLayout(context)
    private val l2 = LinearLayout(context)
    private val l3 = LinearLayout(context)

    init {
        orientation = VERTICAL
        l1.orientation = HORIZONTAL
        l2.orientation = HORIZONTAL
        l3.orientation = HORIZONTAL
    }

    fun setDataList(uriList: List<Uri>?) {
        Log.d("PhotoListView", "setDataList0 size:${uriList?.size ?: 0}")
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
            Log.d("PhotoListView", "setDataList1 ${width}/${height} ${rawUri}")
            img.tag = i
            img.setOnClickListener {
                Log.d("PhotoListView", "itemclick post:${img.tag as Int}")
                mOnClick?.invoke(img, img.tag as Int)
            }
            mRequestBuilder?.load(rawUri)
//                ?.logo()
                ?.into(img)
            if (uriList.size == 1) {
                //按照比例压缩 scale
                if (width > height) {
                    var scale = width / height
                    width = ConvertUtils.dp2px(150f)
                    height = ConvertUtils.dp2px(50f)
                } else if (width < height) {
                    width = ConvertUtils.dp2px(50f)
                    height = ConvertUtils.dp2px(150f)
                }
                val lp = LayoutParams(width, height)
                img.layoutParams = lp
//                img.tag = rawUri
                l1.addView(img, lp)
            } else {
                width = ConvertUtils.dp2px(70f)
                height = ConvertUtils.dp2px(70f)
                if (uriList.size < 4) {
                    val lp = LayoutParams(width, height)
                    img.layoutParams = lp
//                    img.tag = rawUri
                    l1.addView(img, lp)
                } else if (uriList.size == 4) {
                    val lp = LayoutParams(width, height)
                    img.layoutParams = lp
//                    img.tag = rawUri
                    if (i < 2) {
                        l1.addView(img, lp)
                    } else {
                        l2.addView(img, lp)
                    }
                } else {
                    val lp = LayoutParams(width, height)
                    img.layoutParams = lp
//                    img.tag = rawUri
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
