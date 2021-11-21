package com.jamesfchen.bundle2.customview.gesture

import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.almeros.android.multitouch.MoveGestureDetector
import com.almeros.android.multitouch.RotateGestureDetector
import com.almeros.android.multitouch.ShoveGestureDetector
import com.jamesfchen.bundle2.customview.gesture.*


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/10/2019  Tue
 */
class GestureActivity : AppCompatActivity() {
    private lateinit var mGestureDetector: GestureDetector
    private lateinit var mScaleDetector: ScaleGestureDetector
    private lateinit var mRotateDetector: RotateGestureDetector
    private lateinit var mShoveDetector: ShoveGestureDetector
    private lateinit var mMoveGestureDetector: MoveGestureDetector
    private var matrix = Matrix()
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageView = ImageView(this)
//        imageView.scaleType = ImageView.ScaleType.MATRIX
        imageView.setBackgroundColor(Color.BLUE)
        imageView.setOnTouchListener { v, event ->
            mGestureDetector.onTouchEvent(event)
            mScaleDetector.onTouchEvent(event)
            mRotateDetector.onTouchEvent(event)
            mShoveDetector.onTouchEvent(event)
            mMoveGestureDetector.onTouchEvent(event)
            //当down被消费，之后的move才能被传过来消费
            return@setOnTouchListener true
        }
        val lp = ViewGroup.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 600)
        setContentView(imageView, lp)
        mGestureDetector = GestureDetector(this, MyGestureListener())
        mGestureDetector.setOnDoubleTapListener(MyDoubleTapListener())
        mGestureDetector.setContextClickListener(MyContextClickListener())

        mScaleDetector = ScaleGestureDetector(this, MyScaleListener())
        mRotateDetector = RotateGestureDetector(this, MyRotateListener())
        mShoveDetector = ShoveGestureDetector(this, MyShoveListener())
        mMoveGestureDetector = MoveGestureDetector(this, MyMoveListener())
    }

}