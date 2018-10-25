package com.hawksjamesf.simpleweather.util

import android.app.Activity
import android.content.Intent

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 */
inline fun <reified T : Activity> Activity.openActivity(finish: Boolean = true) {
    startActivity(Intent(this, T::class.java))
    if (finish) finish()
}

//typealias Callback:
//inline fun <reified T:Activity> Activity.openActivity(requestCode:Int,callback: Callback){
//    startActivityForResult(Intent(this,T::class.java),requestCode)
//}