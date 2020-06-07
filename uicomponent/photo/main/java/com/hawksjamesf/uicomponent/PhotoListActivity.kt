package com.hawksjamesf.uicomponent

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.HorizontalScrollView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.hawksjamesf.uicomponent.model.PhotoListViewModel
import com.hawksjamesf.uicomponent.repository.CacheRegion
import com.hawksjamesf.uicomponent.repository.PhotoDataSourceFactory
import com.hawksjamesf.uicomponent.repository.PhotoRepository
import com.hawksjamesf.uicomponent.repository.ServiceLocator

class PhotoListActivity : AppCompatActivity() {
    lateinit var rvPhotoList: RecyclerView
    private val viewModel by viewModels<PhotoListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ServiceLocator.instance(this@PhotoListActivity)
                        .getPhotoRepository(CacheRegion.IN_MEMORY_BY_PAGE)
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


        val adapter = PhotoListAdapter(this)
        rvPhotoList.adapter = adapter
        val preloader = RecyclerViewPreloader(adapter.glideRequest, adapter, adapter.preloadSizeProvider, 4)
        rvPhotoList.addOnScrollListener(preloader)
        rvPhotoList.setRecyclerListener { holder ->
            (holder.itemView as HorizontalScrollView).children.forEach {
                adapter.glideRequest.clear(it)
            }
        }
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
//        val observer = Observer(adapter::submitList)
        viewModel.allImages?.observe(this, Observer { list ->
            adapter.submitList(list)
        })
    }
}
