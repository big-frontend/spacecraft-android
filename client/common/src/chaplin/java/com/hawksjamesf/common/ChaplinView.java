package com.hawksjamesf.common;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    private VideoSurfaceView mVsvSurface;
    private VideoTextureView mVtvTexture;
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
        mVsvSurface = rootView.findViewById(R.id.vsv_surface);
        mVtvTexture = rootView.findViewById(R.id.vtv_texture);
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
//            if (mVsvSurface != null) {
//
////                mVsvSurface.layout(surfaceToLeft, surfaceToTop, surfaceToRight, surfaceToBottom);
//            }
//        }
//    }


    private List<Boolean> stickies = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        relayoutByScaleType(mVtvTexture.videoWidth, mVtvTexture.videoHeight, getWidth(), getHeight(), ScaleType.CENTER_INSIDE);
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

    private Matrix mVideoMatrix;

    public void relayoutByScaleType(final int videoWidth, final int videoHeight,
                                    final int containerWidth, final int containerHeight, final ScaleType scaleType) {
        if (videoWidth == 0 || videoHeight == 0 || containerWidth == 0 || containerHeight == 0)
            return;
        if (scaleType == ScaleType.FIT_XY) {
            mVideoMatrix = null;
        } else {
            mVideoMatrix = new Matrix();
            //第1步:把视频区移动到View区,使两者中心点重合.
            float dx = (containerWidth - videoWidth) / 2f;
            float dy = (containerHeight - videoHeight) / 2f;
            mVideoMatrix.setTranslate(dx, dy);

            //第2步:因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来.
            mVideoMatrix.preScale(videoWidth / (containerWidth * 1f), videoHeight / (containerHeight * 1f));

            float sx = containerWidth / (videoWidth * 1f);
            float sy = containerHeight / (videoHeight * 1f);
            float pivotX = containerWidth / 2f;
            float pivotY = containerHeight / 2f;
            if (scaleType == ScaleType.CENTER_CROP) {//裁剪后全屏显示
                //第3步,等比例放大或缩小,直到视频区的一边超过View一边, 另一边与View的另一边相等. 因为超过的部分超出了View的范围,所以是不会显示的,相当于裁剪了.
                float maxScale = Math.max(sx, sy);
                mVideoMatrix.postScale(maxScale, maxScale, pivotX, pivotY);//后两个参数坐标是以整个View的坐标系以参考的
            } else if (scaleType == ScaleType.CENTER_INSIDE) {//全部显示并居中
                //第3步,等比例放大或缩小,直到视频区的一边和View一边相等.如果另一边和view的一边不相等，则留下空隙
                float minScale = Math.min(sx, sy);
                mVideoMatrix.postScale(minScale, minScale, pivotX, pivotY);
            }
            mVtvTexture.setTransform(mVideoMatrix);
        }
    }



    private ScaleType mScaleType;

    public void setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }

        if (mScaleType != scaleType) {
            mScaleType = scaleType;

//            requestLayout();
//            invalidate();
        }
    }

    //<editor-fold desc="开放的接口，API">
    public void start() {
//        mVsvSurface.start();
        mVtvTexture.start();
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

    public void setURI(final Uri uri) {
        setURI(uri, null);
    }

    public void setURI(final Uri uri, final Map<String, String> headers) {
        if (uri == null) {
            throw new NullPointerException("uri param can not be null.");
        }
        mVtvTexture.setVideoURI(uri, headers);
    }
    //</editor-fold>
}
