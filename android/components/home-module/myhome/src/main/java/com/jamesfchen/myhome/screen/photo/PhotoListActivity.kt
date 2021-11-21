package com.jamesfchen.myhome.screen.photo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.FEATURE_ACTION_MODE_OVERLAY
import androidx.appcompat.app.AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.firebase.perf.metrics.AddTrace
import com.jamesfchen.export.AccountService
import com.jamesfchen.myhome.screen.photo.vm.PhotoListViewModel
import com.jamesfchen.myhome.screen.photo.repository.CacheRegion
import com.jamesfchen.myhome.screen.photo.repository.ServiceLocator
import kotlinx.coroutines.flow.collectLatest

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

    @AddTrace(name = "PhotoListActivity#onCreate", enabled = true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isLogin = (ARouter.getInstance().build("/account/exportService").navigation() as? AccountService)?.isLogin()
        Log.d("cjf", "isLogin ${isLogin}")
        //init view
        rvPhotoList = RecyclerView(this)
        rvPhotoList.setBackgroundColor(Color.CYAN)
        rvPhotoList.layoutManager = LinearLayoutManager(this)
//        rvPhotoList.addItemDecoration(Divider(this))
        val  toolbar = Toolbar(this)
        toolbar.setOnClickListener {
            (rvPhotoList.layoutManager as LinearLayoutManager).scrollToPosition(0)
        }
        setSupportActionBar(toolbar)

        val adapter = PhotoListAdapter(this)
//        val headerAdapter = HeaderAdapter()
//        val footerAdapter = FooterAdapter()
//        val concatAdapter = adapter.withLoadStateHeaderAndFooter(headerAdapter, footerAdapter)
        val concatAdapter = adapter
        rvPhotoList.adapter = concatAdapter
//        val preloader = RecyclerViewPreloader(adapter.glideRequestBuilder, adapter, adapter, 4)
//        rvPhotoList.addOnScrollListener(preloader)
//        rvPhotoList.addRecyclerListener { holder ->
//            (holder.itemView as HorizontalScrollView).children.forEach {
//                adapter.glideRequestBuilder.clear(it)
//            }
//        }
        //有固定的size
//        rvPhotoList.setHasFixedSize(true)
//        rvPhotoList.setOnTouchListener { view, motionEvent ->
////            MatrixLog.i("TestPluginListener", "onTouch=$motionEvent")
//            SystemClock.sleep(80)
//            return@setOnTouchListener false
//        }
        setContentView(rvPhotoList)
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
//        }).attachToRecyclerView(rvPhotoList)
//        val observer = Observer(adapter::submitList)
        lifecycleScope.launchWhenResumed {
            viewModel.allImages.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    @AddTrace(name = "PhotoListActivity#onStart", enabled = true)
    override fun onStart() {
        super.onStart()
    }

    @AddTrace(name = "PhotoListActivity#onResume", enabled = true)
    override fun onResume() {
        super.onResume()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }
}
