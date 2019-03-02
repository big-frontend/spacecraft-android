package com.hawksjamesf.common;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/28/2019  Thu
 */
public class ChaplinView extends FrameLayout {
    private static final String TAG = ChaplinView.class.getSimpleName();

    private VideoSurfaceView mSurfaceView;
    private ImageView imageView;

    private Handler mUIHandler;


    public ChaplinView(Context context) {
        super(context);
        initView(null, 0);
    }

    public ChaplinView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(attrs, 0);
    }

    public ChaplinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    private void initView(AttributeSet attributeSet, int defStyleAttr) {
        View rootView = inflate(getContext(), R.layout.view_chaplin, this);
        mSurfaceView = rootView.findViewById(R.id.vsv);
        imageView = rootView.findViewById(R.id.iv);
        mUIHandler = new Handler();
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//
//        // crop view
//        int width = getWidth();
//        int height = getHeight();
//        if (width != 0 && height != 0 && videoWidth != 0 && videoHeight != 0) {
//            float widthFactor = videoWidth / (width * 1.0f);
//            float heightFactor = videoHeight / (height * 1.0f);
//
//            //视频太大，需要居中裁剪
//            int surfaceToLeft = 0;
//            int surfaceToTop = 0;
//            int surfaceToRight = 0;
//            int surfaceToBottom = 0;
//            Log.d(TAG, "onLayout:factor:" + widthFactor + "/" + heightFactor);
//            if (widthFactor > 1 && heightFactor > 1) {//视频宽高都超出
//                float min = Math.min(widthFactor, heightFactor);
////                sWidth = (int) (min * width);
////                sHeight = (int) (min * height);
//                surfaceToLeft = (int) ((width - videoWidth) / 2f + 0.5);
//                surfaceToTop = (int) ((height - videoHeight) / 2f + 0.5);
//                surfaceToRight = width - surfaceToLeft;
//                surfaceToBottom = height - surfaceToTop;
//            } else if (widthFactor > 1) {//视频宽度超出
//                surfaceToLeft = (int) ((width - videoWidth) / 2f + 0.5);
//                surfaceToRight = width - surfaceToLeft;
////                sWidth = (int) (widthFactor * width);
////                surfaceToLeft = (sWidth - videoWidth) / 2;
//
//            } else if (heightFactor > 1) {//视频高度超出
//                surfaceToTop = (int) ((height - videoHeight) / 2f + 0.5);
//                surfaceToBottom = height - surfaceToTop;
////                sHeight = (int) (heightFactor * height);
////                surfaceToTop = (sHeight - videoHeight) / 2;
//
//            }
//
//            Log.d(TAG, "onLayout:" + surfaceToLeft + "/" + surfaceToTop + ":" + surfaceToRight + "/" + surfaceToBottom);
//            if (mSurfaceView != null) {
//
////                mSurfaceView.layout(surfaceToLeft, surfaceToTop, surfaceToRight, surfaceToBottom);
//            }
//        }
//    }


    private List<Boolean> stickies = new ArrayList<>();


    public void start() {
        mSurfaceView.start();
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (imageView.getVisibility() == View.VISIBLE) {
                    imageView.animate().setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            imageView.setVisibility(View.GONE);
                            imageView.setScaleX(1f);
                            imageView.setScaleY(1f);
                            imageView.setAlpha(1f);
                            animation.cancel();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                            .scaleX(2f)
                            .scaleY(2f)
                            .alpha(0.5f)
                            .setDuration(1000)
                            .start();
                }

            }
        });
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d(TAG, "onWindowFocusChanged:hasWindowFocus:" + hasWindowFocus);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
    }
}
