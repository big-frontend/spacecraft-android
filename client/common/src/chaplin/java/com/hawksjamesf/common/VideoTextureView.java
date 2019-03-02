package com.hawksjamesf.common;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import androidx.annotation.RequiresApi;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/02/2019  Sat
 */
public class VideoTextureView extends TextureView implements TextureView.SurfaceTextureListener {
    public static final String TAG = VideoTextureView.class.getSimpleName();

    public VideoTextureView(Context context) {
        super(context);
        initView(null, 0);
    }

    public VideoTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs, 0);
    }

    public VideoTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VideoTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(AttributeSet attributeSet, int defStyleAttr) {
        setSurfaceTextureListener(this);
//        mTextureView.setAlpha(1.0f);
//        mTextureView.setRotation(90.0f);
//        mTextureView.setTransform();
    }

    //surface
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Log.d(TAG + "-Surface", "onSurfaceTextureAvailable");

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        Log.d(TAG + "-Surface", "onSurfaceTextureSizeChanged");

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d(TAG + "-Surface", "onSurfaceTextureDestroyed");
        return false;
    }


    /**
     * 这个回调是实时的，而非用Camera的PreviewCallback这种2次回调的方式。从时间看，基本上每32ms左右上来一帧数据，即每秒30帧，跟本手机的Camera的性能吻合
     *
     * @param surfaceTexture
     */
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        Log.d(TAG + "-Surface", "onSurfaceTextureUpdated");

    }
    //surface
}
