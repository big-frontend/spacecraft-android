package com.hawksjamesf.uicomponent

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
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
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.hawksjamesf.uicomponent.model.Item
import java.util.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jun/07/2020  Sun
 */
class PhotoListAdapter(
        val context: Context
) : PagedListAdapter<Item, RecyclerView.ViewHolder>(diffCallback), ListPreloader.PreloadModelProvider<Item> {
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
//        holder.itemView.tag = viewModel.itemList?.value?.get(position)
        for (uri in item.uriList) {
            val imageView = ImageView(linearLayout.context)
            linearLayout.addView(imageView, LinearLayout.LayoutParams(200, 200))
            Glide.with(imageView.context)
                    .load(uri)
                    .into(imageView)
//                requestBuilder.load(holder.itemView.tag as String?)
//                        .into(imageView)
//                        .clearOnDetach()
            preloadSizeProvider.setView(imageView)

//            viewModel.itemList?.value?.get(index)?.let { urlList.add(it) }

            imageView.setOnClickListener {
                thumbnailBitmapList.clear()
                linearLayout.children.forEach {
                    thumbnailBitmapList.add((it as ImageView).drawToBitmap())
                }
                PhotoActivity.startActivity(context as Activity, thumbnailBitmapList, item.uriList, imageView.tag as Int)
            }
        }

    }

    override fun onCurrentListChanged(previousList: PagedList<Item>?, currentList: PagedList<Item>?) {
        super.onCurrentListChanged(previousList, currentList)
    }

    //    override fun getPreloadItems(position: Int): MutableList<Item> = Collections.singletonList(uriList[position])
    override fun getPreloadItems(position: Int): MutableList<Item> = Collections.emptyList()
    override fun getPreloadRequestBuilder(item: Item): RequestBuilder<Drawable>? = requestBuilder.load(item)
}