package com.jamesfchen.uicomponent

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.drawToBitmap
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.jamesfchen.common.constants.MemoryUnit
import com.jamesfchen.uicomponent.model.Item

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jun/07/2020  Sun
 */
class PhotoListAdapter(
        val context: Context
) : PagedListAdapter<Item, RecyclerView.ViewHolder>(diffCallback)/* ,ListPreloader.PreloadModelProvider<Uri> */ {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
        }
    }

    val glideRequest = Glide.with(context)
    val requestBuilder = glideRequest.asDrawable()
    val preloadSizeProvider = ViewPreloadSizeProvider<Item>()
    val thumbnailBitmapList = arrayListOf<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val scrollView = HorizontalScrollView(parent.context)
        val linearLayout = LinearLayout(parent.context)
        linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        scrollView.addView(linearLayout, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        return object : RecyclerView.ViewHolder(scrollView) {}
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
            requestBuilder.load(uri)
                    .into(imageView)
                    .clearOnDetach()
//            preloadSizeProvider.setView(imageView)

//            viewModel.itemList?.value?.get(index)?.let { urlList.add(it) }

            imageView.setOnClickListener { it ->
                thumbnailBitmapList.clear()
                linearLayout.children.forEach {
                    if (it is ImageView) {
                        thumbnailBitmapList.add(it.drawToBitmap())
                    }
                }
                var bitmapsize = 0f
                for (bitmap in thumbnailBitmapList) {
                    bitmapsize += bitmap.byteCount.toFloat()
                }
                bitmapsize /= MemoryUnit.KB
//                Log.d(Constants.TAG_PHOTO_ACTIVITY, "size:${thumbnailBitmapList.size} curPosition:$i bitmap size:${bitmapsize}k")
                PhotoActivity.startActivityWithThumbnailScaleUp(
                        context as Activity, it,
                        (it as ImageView).drawToBitmap(), it.width / 2, it.height / 2,
                        item.uriList, i
                )
//                PhotoActivity.startActivityWithScaleUp(
//                        context as Activity, it,
//                        it.width / 2, it.height / 2,
//                        it.width,it.height,
//                        item.uriList, i
//                )
            }
        }

    }

    override fun onCurrentListChanged(previousList: PagedList<Item>?, currentList: PagedList<Item>?) {
        super.onCurrentListChanged(previousList, currentList)
    }
//
//    override fun getPreloadItems(position: Int): MutableList<Uri> = Collections.singletonList(getItem(position))
//    override fun getPreloadRequestBuilder(item: Uri): RequestBuilder<Drawable>? = requestBuilder.load(item)
}