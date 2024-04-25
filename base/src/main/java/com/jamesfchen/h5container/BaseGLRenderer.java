package com.jamesfchen.h5container;

import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.Surface;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/20/2021  Sat
 *
 *  cpu将数据纹理化(使数据成为有向图形)
 *  gpu将数据栅格化(使数据成为像素图形)
 *
 * 根据业务使用场景surface的种类：
 * - 地图、视频使用到的surface
 * - android平台通用ui使用到的surface
 * - 图像美白等特效使用到的surface(gl surface)
 *
 * SurfaceFlinger会对屏幕中多个layer(一个layer对应一个surface)进行合成发送到屏幕
 *
 * SurfaceView由于有自己的surface所以，不在app的主线程进行渲染，为了事件同步需要注意线程同步问题
 * TextureView还是依赖于android平台通用ui使用到的surface，其内部主要处理纹理
 *
 *
 */
public class BaseGLRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = BaseGLRenderer.class.getSimpleName();

    private SurfaceTexture mSurfaceTexture;
    private Surface mSurface;

    private int mGlSurfaceTexture;
    private Canvas mSurfaceCanvas;

    private int mTextureWidth;
    private int mTextureHeight;


    public BaseGLRenderer(int mTextureWidth, int mTextureHeight) {
        this.mTextureWidth = mTextureWidth;
        this.mTextureHeight = mTextureHeight;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        synchronized (this) {
            mSurfaceTexture.updateTexImage();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        releaseSurface();
        mGlSurfaceTexture = createTexture();
        if (mGlSurfaceTexture > 0) {
            mSurfaceTexture = new SurfaceTexture(mGlSurfaceTexture);
            mSurfaceTexture.setDefaultBufferSize(mTextureWidth, mTextureHeight);
            mSurface = new Surface(mSurfaceTexture);
        }
    }

    public void releaseSurface() {
        if (mSurface != null) {
            mSurface.release();
        }
        if (mSurfaceTexture != null) {
            mSurfaceTexture.release();
        }
        mSurface = null;
        mSurfaceTexture = null;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        final String extensions = GLES20.glGetString(GLES20.GL_EXTENSIONS);
        Log.d(TAG, extensions);
    }

    private int createTexture() {
        int[] textures = new int[1];

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glGenTextures(1, textures, 0);
        checkGlError("Texture generate");

        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);
        checkGlError("Texture bind");

        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        return textures[0];
    }

    public int getGLSurfaceTexture() {
        return mGlSurfaceTexture;
    }

    public Canvas onDrawViewBegin() {
        mSurfaceCanvas = null;
        if (mSurface != null) {
            try {
                mSurfaceCanvas = mSurface.lockCanvas(null);
            } catch (Exception e) {
                Log.e(TAG, "error while rendering view to gl: " + e);
            }
        }
        return mSurfaceCanvas;
    }

    public void onDrawViewEnd() {
        if (mSurfaceCanvas != null) {
            mSurface.unlockCanvasAndPost(mSurfaceCanvas);
        }
        mSurfaceCanvas = null;
    }


    public void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + GLUtils.getEGLErrorString(error));
        }
    }

    public int getTextureWidth() {
        return mTextureWidth;
    }

    public void setTextureWidth(int textureWidth) {
        mTextureWidth = textureWidth;
    }

    public int getTextureHeight() {
        return mTextureHeight;
    }

    public void setTextureHeight(int textureHeight) {
        mTextureHeight = textureHeight;
    }
}