package com.jamesfchen.uicomponent

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedStateListDrawable
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_drawable.*
import kotlinx.android.synthetic.main.content_drawable.*

class DrawableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        vp.adapter = Adapter()
        vp.currentItem = 5
    }

    inner class Adapter : PagerAdapter() {
        val drawable = arrayListOf<String>(
                "bitmap",
                "shape",
                "clip",
                "inset",
                "scale",
                "rotate",
                "group/layer",
                "group/state list",
                "group/level list",
                "group/transition",
                "group/animation-list",
                "group/animation-selector"
        )


        override fun getPageTitle(position: Int): CharSequence? {
            return drawable[position]
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

        override fun getCount(): Int = drawable.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val frameLayout = FrameLayout(container.context)
            frameLayout.setBackgroundColor(Color.YELLOW)

            var layoutParams: ViewGroup.LayoutParams? = null
            val view = when (drawable[position]) {
                "bitmap" -> {
                    val view = layoutInflater.inflate(R.layout.bitmap, container, false)
                    val bt_scr_over = view.findViewById<Button>(R.id.bt_scr_over)
                    bt_scr_over.background = resources.getDrawable(R.drawable.bitmap).apply {
                        setTintMode(PorterDuff.Mode.SRC_OVER)
                    }

                    view.findViewById<Button>(R.id.bt_src_in).background = resources.getDrawable(R.drawable.bitmap).apply {
                        setTintMode(PorterDuff.Mode.SRC_IN)
                    }

                    view.findViewById<Button>(R.id.bt_scr_atop).background = resources.getDrawable(R.drawable.bitmap).apply {
                        setTintMode(PorterDuff.Mode.SRC_ATOP)
                    }

                    view.findViewById<Button>(R.id.bt_scr_add).background = resources.getDrawable(R.drawable.bitmap).apply { setTintMode(PorterDuff.Mode.ADD) }

                    view.findViewById<Button>(R.id.bt_scr_multiply).background = resources.getDrawable(R.drawable.bitmap).apply {
                        setTintMode(PorterDuff.Mode.MULTIPLY)
                    }

                    view.findViewById<Button>(R.id.bt_scr_screen).background = resources.getDrawable(R.drawable.bitmap).apply {
                        setTintMode(PorterDuff.Mode.SCREEN)
                    }

                    view
                }
//                "shape" -> {
//                }
                "clip" -> {
                    val view = layoutInflater.inflate(R.layout.button_seekbar, container, false)
                    val btn = view.findViewById<Button>(R.id.button)
                    val drawable = resources.getDrawable(R.drawable.clip)
                    btn.background = drawable
                    val sb = view.findViewById<SeekBar>(R.id.seekBar)
                    sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            val max = seekBar?.max
                            val scale = progress.toDouble() / max?.toDouble()!!
                            drawable.level = ((10000 * scale).toInt())
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        }

                    })
                    view

                }
                "inset" -> {
                    val btn = ImageView(container.context)
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                    layoutParams.gravity = Gravity.CENTER
                    val drawable = resources.getDrawable(R.drawable.inset)
                    btn.setImageDrawable(drawable)
                    btn.setBackgroundColor(Color.CYAN)
//                    btn.background = drawable
                    btn
                }
                "scale" -> {
                    val view = layoutInflater.inflate(R.layout.button_seekbar, container, false)
                    val btn = view.findViewById<Button>(R.id.button)
                    val drawable = resources.getDrawable(R.drawable.scale)
                    btn.background = drawable
                    val sb = view.findViewById<SeekBar>(R.id.seekBar)
                    sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            val max = seekBar?.max
                            val scale = progress.toDouble() / max?.toDouble()!!
                            drawable.level = ((10000 * scale).toInt())
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        }

                    })
                    view
                }
                "rotate" -> {
                    val view = layoutInflater.inflate(R.layout.button_seekbar, container, false)
                    val btn = view.findViewById<Button>(R.id.button)
                    val drawable = resources.getDrawable(R.drawable.rotate)
                    btn.background = drawable
                    val sb = view.findViewById<SeekBar>(R.id.seekBar)
                    sb.progress = sb?.max?.div(2) ?: 0
                    drawable.level = 10000 / 2
                    sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            val max = seekBar?.max
                            val scale = progress.toDouble() / max?.toDouble()!!
                            drawable.level = ((10000 * scale).toInt())
//                            drawable.level = progress
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        }

                    })
                    view
                }
//                "group/layer" -> {
//                }
//                "group/state list" -> {
//                }
                "group/level list" -> {
                    val view = layoutInflater.inflate(R.layout.button_seekbar, container, false)
                    val btn = view.findViewById<Button>(R.id.button)
                    val drawable = resources.getDrawable(R.drawable.levellist)
                    btn.background = drawable
                    val sb = view.findViewById<SeekBar>(R.id.seekBar)
                    sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                            val max = seekBar?.max
//                            val scale = progress.toDouble() / max?.toDouble()!!
//                            drawable.level = ((10000 * scale).toInt())
                            drawable.level = progress
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        }

                    })
                    view

                }
                "group/transition" -> {
                    val btn = ImageButton(container.context)
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                    layoutParams.gravity = Gravity.CENTER
                    val drawable: TransitionDrawable = resources.getDrawable(R.drawable.transition) as TransitionDrawable
                    btn.setImageDrawable(drawable)
                    btn.setBackgroundColor(Color.CYAN)
//                    btn.background = drawable
                    drawable.startTransition(3000)
                    btn
                }
                "group/animation-list" -> {
                    val btn = ImageButton(container.context)
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                    layoutParams.gravity = Gravity.CENTER
                    val drawable = resources.getDrawable(R.drawable.animationlist) as AnimationDrawable
                    btn.setImageDrawable(drawable)
                    btn.setBackgroundColor(Color.CYAN)
//                    btn.background = drawable
                    drawable.start()
                    btn
                }
                "group/animation-selector" -> {
                    val btn = ImageButton(container.context)
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                    layoutParams.gravity = Gravity.CENTER
                    val drawable = resources.getDrawable(R.drawable.animatedstatelist) as AnimatedStateListDrawable
//                    btn.setImageDrawable(drawable)
//                    btn.setBackgroundColor(Color.CYAN)
                    btn.background = drawable
//                    drawable.start()
                    btn

                }
                else -> {
                    val btn = Button(container.context)
                    btn.gravity = Gravity.CENTER
                    btn.text = "$position"
                    btn
                }
            }

            if (layoutParams == null) {
                frameLayout.addView(view)
            } else {
                frameLayout.addView(view, layoutParams)
            }
            container.addView(frameLayout)

            return frameLayout
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }

    }


}
