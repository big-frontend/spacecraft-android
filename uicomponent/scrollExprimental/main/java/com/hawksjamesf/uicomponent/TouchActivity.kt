package com.hawksjamesf.uicomponent

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.almeros.android.multitouch.RotateGestureDetector
import com.almeros.android.multitouch.ShoveGestureDetector
import com.almeros.android.multitouch.TwoFingerGestureDetector
import com.hawksjamesf.uicomponent.gesture.MyContextClickListener
import com.hawksjamesf.uicomponent.gesture.MyDoubleTapListener
import com.hawksjamesf.uicomponent.gesture.MyGestureListener


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
    private lateinit var mTwoFingerGestureDetector: TwoFingerGestureDetector

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
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        mGestureDetector.onTouchEvent(event)

        return false
    }

    override fun onGenericMotion(v: View?, event: MotionEvent?): Boolean {
        mGestureDetector.onGenericMotionEvent(event)

        return false
    }


}