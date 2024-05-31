package com.electrolytej.av.recorder

import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Aug/19/2020  Wed
 */
fun Camera?.restartPreview(surfaceTexture: SurfaceTexture) {
    if (this == null) return
    try {
        stopPreview()
    } catch (e: Exception) {
        // ignore: tried to stop a non-existent preview
    }
    try {
        setPreviewTexture(surfaceTexture)
        startPreview()
    } catch (e: Exception) {
        Log.d("restartPreview", "Error starting camera preview: ${e.message}")
    }
}

fun Camera?.restartPreview(surfaceHolder: SurfaceHolder) {
    if (this == null) return
    try {
        stopPreview()
    } catch (e: Exception) {
        // ignore: tried to stop a non-existent preview
    }
    try {
        setPreviewDisplay(surfaceHolder)
        startPreview()
    } catch (e: Exception) {
        Log.d("restartPreview", "Error starting camera preview: ${e.message}")
    }
}