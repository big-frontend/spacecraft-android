package com.jamesfchen.myhome.screen.newfeeds

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.jamesfchen.image.G
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentInfosBinding
import com.jamesfchen.myhome.databinding.ItemPhotoTextBinding
import com.jamesfchen.myhome.screen.newfeeds.model.Item
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import java.util.ArrayList
import java.util.concurrent.TimeUnit
/**
 * 信息流的内容：
 * - 文字
 * - 图片
 * - 视频
 * - 音频
 * - 小程序
 */
class NewFeedsFragment : Fragment() {
    private val newFeedsAdapter:NewFeedsAdapter by lazy {
        val a = NewFeedsAdapter(requireContext())
//        val headerAdapter = HeaderAdapter()
//        val footerAdapter = FooterAdapter()
//       return@lazy a.withLoadStateHeaderAndFooter(headerAdapter, footerAdapter)
        return@lazy a
    }
    private val rvNewFeeds: RecyclerView by lazy {
        return@lazy RecyclerView(requireContext()).apply {
            setBackgroundColor(Color.CYAN)
            layoutManager = LinearLayoutManager(context)
            //        addItemDecoration(Divider(this@NewFeedsActivity))
            adapter = newFeedsAdapter
//        val preloader = RecyclerViewPreloader(adapter.glideRequestBuilder, adapter, adapter, 4)
//        rvNewFeeds.addOnScrollListener(preloader)
//        rvNewFeeds.addRecyclerListener { holder ->
//            (holder.itemView as HorizontalScrollView).children.forEach {
//                adapter.glideRequestBuilder.clear(it)
//            }
//        }
            //有固定的size
//        rvNewFeeds.setHasFixedSize(true)
//        rvNewFeeds.setOnTouchListener { view, motionEvent ->
////            MatrixLog.i("TestPluginListener", "onTouch=$motionEvent")
//            SystemClock.sleep(80)
//            return@setOnTouchListener false
//        }
        }
    }
//    private val viewModel by viewModels<NewFeedsViewModel> {
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return NewFeedsViewModel(application) as T
//            }
//        }
//    }
    private val viewModel by viewModels<NewFeedsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return rvNewFeeds
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val isLogin = (ARouter.getInstance().build("/account/exportService").navigation() as? AccountService)?.isLogin()
//        Log.d("cjf", "isLogin ${isLogin}")
        lifecycleScope.launchWhenCreated { }
        //init data
//        ItemTouchHelper(object : ItemTouchHelper.Callback() {
//            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
//                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
//
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
////                viewModel.itemList?.value?.remove(viewHolder.itemView.tag)
//            }
//        }).attachToRecyclerView(rvNewFeeds)
//        val observer = Observer(newFeedsAdapter::submitList)
        lifecycleScope.launchWhenResumed {
            viewModel.newFeeds.collectLatest { pagingData ->
                newFeedsAdapter.submitData(pagingData)
            }
        }
    }
}
/**
 *
 * 由于android动画代价比较大，所以进行列表图片加载的时候最好不要开启过渡效果
 */

class NewFeedsAdapter(
    val context: Context
) : PagingDataAdapter<Item, NewFeedsAdapter.MyViewHolder>(diffCallback),
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
        holder.binding.tvText.setText("photo list size:${item.photoList?.size} ${item.text}")
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
                item.photoList?.toMutableList() as? ArrayList<Uri>, pos
            )
//                PhotoActivity.startActivityWithScaleUp(
//                        context as Activity, it,
//                        it.width / 2, it.height / 2,
//                        it.width,it.height,
//                        item.uriList, i
//                )
        }
//        holder.binding.ilvPhotoList.setViewPreloadSizeProvider(viewPreloadSizeProvider)
        holder.binding.ilvPhotoList.setDataList(item.photoList)

    }

    override fun getPreloadItems(position: Int): MutableList<Uri> {
        return getItem(position)?.photoList?.toMutableList() ?: mutableListOf()
    }

    override fun getPreloadRequestBuilder(uri: Uri): RequestBuilder<*> {
        return glideRequestBuilder.load(uri)
    }

    override fun getPreloadSize(uri: Uri, adapterPosition: Int, perItemPosition: Int) =
        viewPreloadSizeProvider.getPreloadSize(uri, adapterPosition, perItemPosition)
}