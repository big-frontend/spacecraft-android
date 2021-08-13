package com.jamesfchen.myhome.screen.photo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.HorizontalScrollView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jamesfchen.myhome.screen.photo.vm.PhotoListViewModel
import com.jamesfchen.myhome.screen.photo.repository.CacheRegion
import com.jamesfchen.myhome.screen.photo.repository.ServiceLocator
import jamesfchen.widget.Divider
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.anko.coroutines.experimental.asReference

class PhotoListActivity : AppCompatActivity() {
    lateinit var rvPhotoList: RecyclerView
    private val viewModel by viewModels<PhotoListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
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
//        val preloader = RecyclerViewPreloader(adapter.glideRequest, adapter, adapter.preloadSizeProvider, 4)
//        rvPhotoList.addOnScrollListener(preloader)
        rvPhotoList.addRecyclerListener { holder ->
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
       lifecycleScope.launchWhenResumed {
           viewModel.allImages.collectLatest { pagingData ->
               adapter.submitData(pagingData)
           }
       }
    }
}
