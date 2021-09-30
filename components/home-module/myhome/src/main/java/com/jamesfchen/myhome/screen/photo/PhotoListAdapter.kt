package com.jamesfchen.myhome.screen.photo

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.jamesfchen.loader.G
import com.jamesfchen.myhome.screen.photo.model.Item
import java.util.*
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.jamesfchen.myhome.databinding.ItemPhotoTextBinding
import com.jamesfchen.myhome.widget.CollapseTextView


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
) : PagingDataAdapter<Item, PhotoListAdapter.MyViewHolder>(diffCallback),
    ListPreloader.PreloadModelProvider<Uri>, ListPreloader.PreloadSizeProvider<Uri> {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
        }
        private const val imageWidthPixels = 1024;
        private const val imageHeightPixels = 768;
    }

    val glideRequestBuilder = G.with(context)

    //ViewPreloadSizeProvider:RecyclerView 里有统一的 View 尺寸、你使用 into(ImageView)来加载图片并且你没有使用 override() 方法来设置一个不同的尺寸
    //FixedPreloadSizeProvider:使用 override() 方法或其他情况导致加载的图片尺寸并不完全匹配你的 View 尺寸
    val viewPreloadSizeProvider = ViewPreloadSizeProvider<Uri>()
    val fixedPreloadSizeProvider =
        FixedPreloadSizeProvider<Uri>(imageWidthPixels, imageHeightPixels);

    class MyViewHolder(val binding: ItemPhotoTextBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPhotoTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

  
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.binding.tvIndex.text = "$position"
        holder.binding.tvText.setText("photo list size:${item.uriList.size} afadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafaafadsfasdfasfasfdsafasfafasdfsdafasfsafa")
        holder.binding.tvText.autoRecoverStateByUniqueKey(position)

        holder.binding.ilvPhotoList.setOnItemClickListener { _, pos ->
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

            PhotoActivity.startActivity(
                context as Activity,
                item.uriList.toMutableList() as ArrayList<Uri>?, pos
            )
//                PhotoActivity.startActivityWithScaleUp(
//                        context as Activity, it,
//                        it.width / 2, it.height / 2,
//                        it.width,it.height,
//                        item.uriList, i
//                )
        }
//        holder.binding.ilvPhotoList.setViewPreloadSizeProvider(viewPreloadSizeProvider)
        holder.binding.ilvPhotoList.setDataList(item.uriList)

    }

    override fun getPreloadItems(position: Int): MutableList<Uri> {
        return getItem(position)?.uriList?.toMutableList() ?: mutableListOf()
    }

    override fun getPreloadRequestBuilder(uri: Uri): RequestBuilder<*> {
        return glideRequestBuilder.load(uri)
    }

    override fun getPreloadSize(uri: Uri, adapterPosition: Int, perItemPosition: Int) =
        viewPreloadSizeProvider.getPreloadSize(uri, adapterPosition, perItemPosition)
}