package com.jamesfchen.bundle2.customview.testStyle

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.jamesfchen.bundle2.R
import com.jamesfchen.bundle2.databinding.ActivityTextStyleBinding

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/16/2019  Sat
 */
class TextStyleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        layoutInflater.factory2 = MyLayoutInflaterFactoryV2()
//        layoutInflater.factory = MyLayoutInflaterFactory()
        super.onCreate(savedInstanceState)
//        val field = LayoutInflater::class.java.getDeclaredField("mFactorySet")
//        field.isAccessible = true
//        field.setBoolean(layoutInflater, false)
        val binding= ActivityTextStyleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvFont.setOnClickListener{
//            val request = FontRequest(
//                    "com.google.android.gms.fonts",
//                    "com.google.android.gms",
//                    "Alfa Slab One",
//                    R.array.com_google_android_gms_fonts_certs
//            )
//            val callback = object : FontsContract.FontRequestCallback() {
//
//                override fun onTypefaceRetrieved(typeface: Typeface) {
//                    // Your code to use the font goes here
//                }
//
//                override fun onTypefaceRequestFailed(reason: Int) {
//                    // Your code to deal with the failure goes here
//                }
//            }
//            FontsContractCompat.requestFonts(this@TextStyleActivity, request, callback,handler)
            val font = ResourcesCompat.getFont(this@TextStyleActivity, R.font.alfa_slab_one)
            binding.tvFont.typeface =font
        }

    }

    override fun onApplyThemeResource(theme: Resources.Theme?, resid: Int, first: Boolean) {
        super.onApplyThemeResource(theme, resid, first)
    }

}