package com.hawksjamesf.uicomponent

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
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

    lateinit var rvPhotoList: RecyclerView
    val itemList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvPhotoList = RecyclerView(this)
        rvPhotoList.setBackgroundColor(Color.CYAN)
        rvPhotoList.layoutManager = LinearLayoutManager(this)
        rvPhotoList.addItemDecoration(Divider(this))
        val adapter = PhotoListAdapter()
        rvPhotoList.adapter = adapter
        setContentView(rvPhotoList)
        val storage = FirebaseStorage.getInstance("gs://spacecraft-22dc1.appspot.com")
        val storageRef = storage.reference
//        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
        //        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
        for (i in 168..176) {
            Log.d("hawks", "index:$i")
            storageRef.child("WechatIMG$i.jpeg").downloadUrl
                    .addOnCompleteListener { task ->
                        Log.d("hawks", "url:" + task.result.toString())
                        itemList.add(task.result.toString())
                        adapter.notifyDataSetChanged()
                    }
        }

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                (viewHolder as CheeseViewHolder).cheese?.let {
//                    viewModel.remove(it)
//                }
            }
        }).attachToRecyclerView(rvPhotoList)
        val observer = Observer(adapter::submitList)
//        viewModel.allCheeses.observe(this,observer)

    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        }
    }

    inner class PhotoListAdapter : PagedListAdapter<String, RecyclerView.ViewHolder>(diffCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val scrollView = HorizontalScrollView(parent.context)
            val linearLayout = LinearLayout(parent.context)
            linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            scrollView.addView(linearLayout, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            repeat(itemList.size) { _ ->
                val image = ImageView(parent.context)
                linearLayout.addView(image, LinearLayout.LayoutParams(200, 200))
            }
            return object : RecyclerView.ViewHolder(scrollView) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val linearLayout = (holder.itemView as HorizontalScrollView).getChildAt(0) as LinearLayout
            for (index in 0 until itemCount) {
                val imageView = linearLayout.getChildAt(index) as? ImageView
                imageView ?: continue
                Glide.with(imageView.context)
                        .load(itemList[index])
                        .into(imageView)
            }
        }

        override fun getItemCount(): Int = itemList.size

    }


//    inner class PhotoListViewHolder:RecyclerView.ViewHolder(){}
}