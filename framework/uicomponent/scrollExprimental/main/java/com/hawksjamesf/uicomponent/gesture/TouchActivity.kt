package com.hawksjamesf.uicomponent.gesture

import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.almeros.android.multitouch.MoveGestureDetector
import com.almeros.android.multitouch.RotateGestureDetector
import com.almeros.android.multitouch.ShoveGestureDetector


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/10/2019  Tue
 */
class TouchActivity : AppCompatActivity(), View.OnTouchListener, View.OnGenericMotionListener {
    private lateinit var mGestureDetector: GestureDetector
    private lateinit var mScaleDetector: ScaleGestureDetector
    private lateinit var mRotateDetector: RotateGestureDetector
    private lateinit var mShoveDetector: ShoveGestureDetector
    private lateinit var mMoveGestureDetector: MoveGestureDetector
    private var matrix=Matrix()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.MATRIX
        imageView.setBackgroundColor(Color.BLUE)
        val lp = ViewGroup.LayoutParams(40, 40)
        setContentView(imageView, lp)

        mGestureDetector = GestureDetector(this, MyGestureListener())
        mGestureDetector.setOnDoubleTapListener(MyDoubleTapListener())
        mGestureDetector.setContextClickListener(MyContextClickListener())

        mScaleDetector= ScaleGestureDetector(this,MyScaleListener())
        mRotateDetector = RotateGestureDetector(this,MyRotateListener())
        mShoveDetector = ShoveGestureDetector(this,MyShoveListener())
        mMoveGestureDetector= MoveGestureDetector(this,MyMoveListener())
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        mGestureDetector.onTouchEvent(event)
        mScaleDetector.onTouchEvent(event)
        mRotateDetector.onTouchEvent(event)
        mShoveDetector.onTouchEvent(event)
        mMoveGestureDetector.onTouchEvent(event)


        return true
    }

    override fun onGenericMotion(v: View?, event: MotionEvent?): Boolean {
        mGestureDetector.onGenericMotionEvent(event)

        return false
    }


}