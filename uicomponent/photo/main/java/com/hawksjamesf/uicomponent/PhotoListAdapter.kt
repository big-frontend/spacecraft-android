package com.hawksjamesf.uicomponent

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.drawToBitmap
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.hawksjamesf.uicomponent.model.Item

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
        for (i in 0 until item.uriList.size) {
            val uri = item.uriList[i]
            val imageView = ImageView(linearLayout.context)
            imageView.tag = i
            linearLayout.addView(imageView, LinearLayout.LayoutParams(200, 200))
            Glide.with(imageView.context)
                    .load(uri)
                    .into(imageView)
            requestBuilder.load(uri)
                    .into(imageView)
                    .clearOnDetach()
            preloadSizeProvider.setView(imageView)

//            viewModel.itemList?.value?.get(index)?.let { urlList.add(it) }

            imageView.setOnClickListener {
                thumbnailBitmapList.clear()
                linearLayout.children.forEach {
                    thumbnailBitmapList.add((it as ImageView).drawToBitmap())
                }
                PhotoActivity.startActivity(context as Activity, thumbnailBitmapList, item.uriList, i)
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