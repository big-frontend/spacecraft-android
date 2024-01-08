package com.jamesfchen.main

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

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
class MyGlSurfaceView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    GLSurfaceView(context, attrs) {
    init {
        setEGLContextClientVersion(2)
        setRenderer(MyRenderer())
        renderMode = RENDERMODE_WHEN_DIRTY
    }
}