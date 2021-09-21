package com.jamesfchen.myhome.screen.photo

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.drawToBitmap
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.jamesfchen.common.constants.MemoryUnit
import com.jamesfchen.loader.G
import com.jamesfchen.myhome.screen.photo.model.Item
import java.util.*
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnPreDrawListener
import com.bumptech.glide.util.FixedPreloadSizeProvider


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jun/07/2020  Sun
 *
 * 由于android动画代价比较大，所以进行列表图片加载的时候最好不要开启过渡效果
 */
class PhotoListAdapter(
    val context: Context
) : PagingDataAdapter<Item, RecyclerView.ViewHolder>(diffCallback),
    ListPreloader.PreloadModelProvider<Item>, ListPreloader.PreloadSizeProvider<Item> {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
        }
        private const val imageWidthPixels = 1024;
        private const val imageHeightPixels = 768;
    }

    val glideRequestBuilder = G.with(context)
    val glideDrawableRequest = glideRequestBuilder.asDrawable()
    val gldeGifRequest = glideRequestBuilder.asGif()
    val gldeBitmapRequest = glideRequestBuilder.asBitmap()

    //ViewPreloadSizeProvider:RecyclerView 里有统一的 View 尺寸、你使用 into(ImageView)来加载图片并且你没有使用 override() 方法来设置一个不同的尺寸
    //FixedPreloadSizeProvider:使用 override() 方法或其他情况导致加载的图片尺寸并不完全匹配你的 View 尺寸
    val viewPreloadSizeProvider = ViewPreloadSizeProvider<Item>()
    val fixedPreloadSizeProvider =
        FixedPreloadSizeProvider<Item>(imageWidthPixels, imageHeightPixels);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val scrollView = HorizontalScrollView(parent.context)
        val linearLayout = LinearLayout(parent.context)
        linearLayout.layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        scrollView.addView(
            linearLayout,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        return object : RecyclerView.ViewHolder(scrollView) {}
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        val linearLayout = (holder.itemView as HorizontalScrollView).getChildAt(0) as LinearLayout
        linearLayout.removeAllViews()
        val textview = TextView(linearLayout.context)
        textview.text = "$position"
        textview.setTextColor(Color.BLACK)
        linearLayout.addView(textview, LinearLayout.LayoutParams(200, 200))
        for (i in 0 until item.uriList.size) {
            val uri = item.uriList[i]
            val imageView = ImageView(linearLayout.context)
            imageView.tag = uri
            linearLayout.addView(imageView, LinearLayout.LayoutParams(200, 200))
            glideDrawableRequest.load(uri)
//                .options(HomeExtension.TIMEOUT_MS,1000L)
                .logo()
                .into(imageView)
                .clearOnDetach()
            viewPreloadSizeProvider.setView(imageView)

//            viewModel.itemList?.value?.get(index)?.let { urlList.add(it) }

            imageView.setOnClickListener { it ->
//                val thumbnailBitmapList = arrayListOf<Bitmap>()
//                linearLayout.children.forEach {
//                    if (it is ImageView) {
//                        thumbnailBitmapList.add(it.drawToBitmap())
//                    }
//                }
//                var bitmapsize = 0f
//                for (bitmap in thumbnailBitmapList) {
//                    bitmapsize += bitmap.byteCount.toFloat()
//                }
//                PhotoActivity.startActivityWithThumbnailScaleUp(
//                    context as Activity, it,
//                    (it as ImageView).drawToBitmap(), it.width / 2, it.height / 2,
//                    item.uriList, i
//                )

                PhotoActivity.startActivity(context as Activity,
                    item.uriList.toMutableList() as ArrayList<Uri>?, i)
//                PhotoActivity.startActivityWithScaleUp(
//                        context as Activity, it,
//                        it.width / 2, it.height / 2,
//                        it.width,it.height,
//                        item.uriList, i
//                )
            }
        }
    }

    override fun getPreloadItems(position: Int): MutableList<Item> {
        return Collections.singletonList(getItem(position))
    }

    override fun getPreloadRequestBuilder(item: Item): RequestBuilder<*> {
        return glideRequestBuilder.load(item.uriList[0])
    }

    override fun getPreloadSize(item: Item, adapterPosition: Int, perItemPosition: Int) =
        viewPreloadSizeProvider.getPreloadSize(item, adapterPosition, perItemPosition)
}