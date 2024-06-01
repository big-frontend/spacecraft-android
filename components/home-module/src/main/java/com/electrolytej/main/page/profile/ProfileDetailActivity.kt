package com.electrolytej.main.page.profile

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.electrolytej.main.databinding.ActivityProfileDetailBinding
import com.electrolytej.main.databinding.ActivityProfileDetailSkeletonBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

/**
 * 转场动画设计存在着性能问题在低端机上面表现卡顿，高端机轻微卡顿
 * - 容易出现oom，图片倒影会createBitmap，如果内存不足就会oom，还会频繁触发gc，导致主线程卡顿
 * - 转场+骨架屏呼吸动画的时候还会发送多个网络请求，大量的抢夺cpu
 * - 代码中的不合理 指定动画的时候迭代骨架屏中所有的具备呼吸的控件，传输的size为对象
 */
class ProfileDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileDetailBinding
    lateinit var skeletonBinding :ActivityProfileDetailSkeletonBinding

    lateinit var contentFrameLayout: FrameLayout

    companion object {
        fun openActivity(activity: Context, startX: Int, startY: Int, height: Int) {
            val intent = Intent(activity, ProfileDetailActivity::class.java)
            intent.putExtra("startX", startX)
            intent.putExtra("startY", startY)
            intent.putExtra("height", height)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        skeletonBinding = ActivityProfileDetailSkeletonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewAttrs = ViewAttrs(
            intent.getIntExtra("startX", 0),
            intent.getIntExtra("startY", 0),
            intent.getIntExtra("height", 0)
        )
        contentFrameLayout = findViewById(android.R.id.content)
        val layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        contentFrameLayout.addView(skeletonBinding.root,layoutParams)
        binding.root.visibility = View.GONE
        overridePendingTransition(0,0)
        skeletonBinding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                skeletonBinding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                startTranstionImageAnimation()
            }

        })
        lifecycleScope.launchWhenResumed {
            flowOf(true)
                .flowOn(Dispatchers.IO)
                .onStart {
                    delay(4000)
                }.collect {
                    isDetailServiceComplete = it
                }
        }
    }

    data class ViewAttrs(val startX: Int, val startY: Int, val height: Int)

    lateinit var mViewAttrs: ViewAttrs
    fun startTranstionImageAnimation() {
        val fromY = mViewAttrs.startY
        val fromHeight: Int = skeletonBinding.root.height
        val endHeight = mViewAttrs.height
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.duration = 320
        anim.addUpdateListener(AnimatorUpdateListener { animation ->
            if (isFinishing) {
                anim.cancel()
                return@AnimatorUpdateListener
            }
            if (animation == null) {
                return@AnimatorUpdateListener
            }
            val currentValue = animation.animatedValue as Float
            val layoutParams = skeletonBinding.root.layoutParams as FrameLayout.LayoutParams

            //进入详情界面
            layoutParams.topMargin = (fromY * (1.0f - currentValue)).toInt()
            layoutParams.height = (endHeight + (fromHeight - endHeight) * currentValue).toInt()
            skeletonBinding.root.requestLayout()
            if (currentValue == 1f) {
                //进行动画
                isAnmiComplete = true
                startNewLoading()
                binding.root.visibility = View.VISIBLE
            }
        })
        anim.start()
    }

    var isDetailServiceComplete = false
    var isAnmiComplete = false
    private lateinit var fadeOutAlphaAnimation: ValueAnimator
    private fun startNewLoading() {
        if (isDetailServiceComplete) {
            stopNewLoading()
            return
        }
        fadeOutAlphaAnimation = ValueAnimator.ofFloat(1f, 0.5f, 1f)
        fadeOutAlphaAnimation.addUpdateListener(AnimatorUpdateListener { animation ->
            if (isFinishing) {
                return@AnimatorUpdateListener
            }
            if (animation.animatedValue is Float) {
                val alpha = animation.animatedValue as Float
                val list = mutableListOf<View>()
                findViewByTag(skeletonBinding.root, "breathing", list)
                for (view in list) {
                    view.alpha = alpha
                }
            }
        })
        fadeOutAlphaAnimation.duration = 800
        fadeOutAlphaAnimation.repeatCount = -1
        fadeOutAlphaAnimation.repeatMode = ValueAnimator.RESTART
        fadeOutAlphaAnimation.start()
    }

    fun findViewByTag(rootView: ViewGroup?, tag: String, list: MutableList<View>) {
        if (rootView == null) {
            return
        }
        val childCount = rootView.childCount
        for (i in 0 until childCount) {
            val childAt = rootView.getChildAt(i)
            if (childAt is ViewGroup) {
                if (tag == childAt.getTag()) {
                    list.add(childAt)
                }
                findViewByTag(childAt, tag, list)
            } else {
                if (tag == childAt.tag) {
                    list.add(childAt)
                }
            }
        }
    }

    fun stopNewLoading() {
        isDetailServiceComplete = true
        fadeOutAlphaAnimation.cancel()
        if (isFinishing) {
            return
        }
        if (isAnmiComplete) {
            skeletonBinding.root.clearAnimation()
            contentFrameLayout.removeView(skeletonBinding.root)
        }
    }
}