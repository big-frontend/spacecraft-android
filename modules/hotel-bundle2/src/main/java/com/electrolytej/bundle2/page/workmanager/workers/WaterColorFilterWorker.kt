package com.electrolytej.bundle2.page.workmanager.workers

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.RenderScript
import androidx.work.WorkerParameters

class WaterColorFilterWorker(context: Context, parameters: WorkerParameters) : BaseFilterWorker(context, parameters) {

    override fun applyFilter(input: Bitmap): Bitmap {
        var rsContext: RenderScript? = null
        try {
            val output = Bitmap
                    .createBitmap(input.width, input.height, input.config!!)
            rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.DEBUG)
            val inAlloc = Allocation.createFromBitmap(rsContext, input)
            val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
            // The Renderscript function that generates the water color effect is defined in
            // `src/main/rs/.rs`. The main idea, is to select a window of the image
            // and then find the most dominant pixel value. Then we set the r, g, b, channels of the
            // pixels to the one with the dominant pixel value.
//            val oilFilterEffect = ScriptC_waterColorEffect(rsContext)
//            oilFilterEffect._script = oilFilterEffect
//            oilFilterEffect._width = input.width.toLong()
//            oilFilterEffect._height = input.height.toLong()
//            oilFilterEffect._in = inAlloc
//            oilFilterEffect._out = outAlloc
//            oilFilterEffect.invoke_filter()
//            outAlloc.copyTo(output)
            return output
        } finally {
            rsContext?.finish()
        }
    }
}