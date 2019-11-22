package com.hawksjamesf.uicomponent

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage

class PhotoListActivity : AppCompatActivity() {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        }
        const val BASE_URL = "gs://spacecraft-22dc1.appspot.com"
    }

    lateinit var rvPhotoList: RecyclerView
    private val viewModel by viewModels<PhotoListViewModel>()
//    lateinit var pagedList :PagedList
    val urlList = listOf<String>(
            "https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG168.jpeg?alt=media&token=2a28b72e-d8a5-4c0f-b919-853722c850a2",
            "https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG173.jpeg?alt=media&token=67c4a264-1ab4-47ca-971c-25e1571ed084",
            "https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG175.jpeg?alt=media&token=6596738d-e940-4158-bfa9-2f16d2586865",
            "https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG174.jpeg?alt=media&token=331f983e-e69c-4156-ab3b-d82980f6ad91",
            "https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4"
    )

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
//        pagedList=PagedList.Builder
        val storage = FirebaseStorage.getInstance(BASE_URL)
        val storageRef = storage.reference
        if (urlList.isNotEmpty()) {
            urlList.forEach {
                viewModel.itemList.value?.add(it)
            }
        } else {
//        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
            //        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
            for (i in 168..176) {
                Log.d("hawks", "index:$i")
                storageRef.child("WechatIMG$i.jpeg").downloadUrl
                        .addOnCompleteListener { task ->
                            Log.d("hawks", "url:" + task.result.toString())
//                        itemList.add(task.result.toString())
//                        adapter.notifyDataSetChanged()
                            viewModel.itemList.value?.add(task.result.toString())
                        }
            }
        }
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.itemList.value?.remove(viewHolder.itemView.tag)
            }
        }).attachToRecyclerView(rvPhotoList)
        val observer = Observer(adapter::submitList)
        viewModel.itemList.observe(this, observer)
//        viewModel.itemList.

    }

    inner class PhotoListAdapter : PagedListAdapter<String, RecyclerView.ViewHolder>(diffCallback) {
        //        val itemList = mutableListOf<String>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val scrollView = HorizontalScrollView(parent.context)
            val linearLayout = LinearLayout(parent.context)
            linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            scrollView.addView(linearLayout, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            repeat(itemCount) { _ ->
                val image = ImageView(parent.context)
                linearLayout.addView(image, LinearLayout.LayoutParams(200, 200))
            }
            return object : RecyclerView.ViewHolder(scrollView) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val linearLayout = (holder.itemView as HorizontalScrollView).getChildAt(0) as LinearLayout
            holder.itemView.setTag(viewModel.itemList.value?.get(position))
            for (index in 0 until itemCount) {
                val imageView = linearLayout.getChildAt(index) as? ImageView
                imageView ?: continue
                Glide.with(imageView.context)
                        .load(viewModel.itemList.value?.get(index))
                        .into(imageView)
            }
        }

        override fun getItemCount(): Int = viewModel.itemList.value?.size ?: 0

    }


//    inner class PhotoListViewHolder:RecyclerView.ViewHolder(){}
}