package com.electrolytej.main.page.photo

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.electrolytej.main.databinding.ActivityGalleryBinding
import com.electrolytej.main.databinding.ItemDumpBinding
import com.electrolytej.main.widget.carousel.CarouselPagerAdapter
import com.electrolytej.main.R

class GalleryActivity : Activity() {
    var list = listOf(1, 2, 3, 4, 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityGalleryBinding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGallery.adapter = MyAdapter()
        binding.btSetWallpaper.setOnClickListener { }

        val adapter = Adapter()
        binding.cv.setAdapter(adapter)
//        binding.cv.setPageTransformer(ZoomOutPageTransformer())
        binding.cv.setPageTransformer(com.electrolytej.main.widget.carousel.transformer.ParallaxPageTransformer())
        adapter.setDataList(list)

        for (i in 0..3) {
            val itemBinding: ItemDumpBinding = ItemDumpBinding.inflate(layoutInflater, null, false)
            itemBinding.tvName.text = i.toString()
            val position = i % 5
            if (position == 0) {
                itemBinding.tvName.setBackgroundColor(Color.BLUE)
            } else if (position == 1) {
                itemBinding.tvName.setBackgroundColor(Color.DKGRAY)
            } else if (position == 2) {
                itemBinding.tvName.setBackgroundColor(Color.YELLOW)
            } else if (position == 3) {
                itemBinding.tvName.setBackgroundColor(Color.RED)
            } else if (position == 4) {
                itemBinding.tvName.setBackgroundColor(Color.CYAN)
            }
            binding.danmu.addView(
                itemBinding.root,
                i,
                ViewGroup.LayoutParams(ConvertUtils.dp2px(240f), ConvertUtils.dp2px(100f))
            )
        }
//        binding.danmu.hideAnimation(null)
        binding.danmu.setInAnimation(this, R.anim.anim_marquee_in)
        binding.danmu.setOutAnimation(this, R.anim.anim_marquee_out)
        binding.danmu.animateFirstView = false
        binding.danmu.showAnimation()
    }


    internal class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_photo, parent, false)
            )
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.itemView.setOnClickListener { v: View? -> }
        }

        override fun getItemCount(): Int {
            return 13
        }
    }

    internal class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class Adapter : CarouselPagerAdapter<Int>() {
        override fun instantiatePager(container: ViewGroup, position: Int): Any {
            val imageView = ImageView(container.context)
            when (position % 5) {
                0 -> {
                    imageView.setBackgroundColor(Color.BLUE)
                }

                1 -> {
                    imageView.setBackgroundColor(Color.BLACK)
                }

                2 -> {
                    imageView.setBackgroundColor(Color.YELLOW)
                }

                3 -> {
                    imageView.setBackgroundColor(Color.RED)
                }

                4 -> {
                    imageView.setBackgroundColor(Color.CYAN)
                }
            }
            val layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            imageView.layoutParams = layoutParams
            container.addView(imageView)
            return imageView
        }

        override fun destroyPager(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getPagers() = dataList.size
    }
}