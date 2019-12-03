package com.hawksjamesf.uicomponent

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.drawToBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hawksjamesf.uicomponent.repository.CacheRegion
import com.hawksjamesf.uicomponent.repository.ServiceLocator

class PhotoListActivity : AppCompatActivity() {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        }

    }

    lateinit var rvPhotoList: RecyclerView
    private val viewModel by viewModels<PhotoListViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ServiceLocator.instance(this@PhotoListActivity).getPhotoRepository(CacheRegion.IN_MEMORY_BY_PAGE)
                @Suppress("UNCHECKED_CAST")
                return PhotoListViewModel(application, repo) as T
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //init view
        rvPhotoList = RecyclerView(this)
        rvPhotoList.setBackgroundColor(Color.CYAN)
        rvPhotoList.layoutManager = LinearLayoutManager(this)
        rvPhotoList.addItemDecoration(Divider(this))
        val adapter = PhotoListAdapter()
        rvPhotoList.adapter = adapter
        setContentView(rvPhotoList)

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
//        }).attachToRecyclerView(rvPhotoList)
        val observer = Observer(adapter::submitList)
        viewModel.itemList?.observe(this, observer)
    }

    inner class PhotoListAdapter : PagedListAdapter<String, RecyclerView.ViewHolder>(diffCallback) {

        val thumbnailBitmapList = arrayListOf<Bitmap>()
        val urlList = arrayListOf<String>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val scrollView = HorizontalScrollView(parent.context)
            val linearLayout = LinearLayout(parent.context)
            linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            scrollView.addView(linearLayout, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            repeat(itemCount) {
                val image = ImageView(parent.context)
                image.tag = it
                linearLayout.addView(image, LinearLayout.LayoutParams(200, 200))
            }
            return object : RecyclerView.ViewHolder(scrollView) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            val linearLayout = (holder.itemView as HorizontalScrollView).getChildAt(0) as LinearLayout
            holder.itemView.tag = viewModel.itemList?.value?.get(position)
            for (index in 0 until itemCount) {
                val imageView = linearLayout.getChildAt(index) as? ImageView
                imageView ?: continue
                Glide.with(imageView.context)
                        .load(viewModel.itemList?.value?.get(index))
                        .into(imageView)
                viewModel.itemList?.value?.get(index)?.let { urlList.add(it) }

                imageView.setOnClickListener {
                    thumbnailBitmapList.clear()
                    linearLayout.children.forEach {
                        thumbnailBitmapList.add((it as ImageView).drawToBitmap())
                    }
                    PhotoActivity.startActivity(this@PhotoListActivity, thumbnailBitmapList, urlList, imageView.tag as Int)
                }
            }
        }

        override fun onCurrentListChanged(previousList: PagedList<String>?, currentList: PagedList<String>?) {
            super.onCurrentListChanged(previousList, currentList)
        }
    }

}