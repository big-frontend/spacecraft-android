package com.jamesfchen.uicomponent

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/16/2019  Sat
 */
const val TAG="MyLayoutInflaterFactory"
class MyLayoutInflaterFactoryV2 : LayoutInflater.Factory2 {
    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        Log.d(TAG,"onCreateView 1 :$parent $name")
        return null
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        Log.d(TAG,"onCreateView 2:$name")
        return null
    }
}

class MyLayoutInflaterFactory : LayoutInflater.Factory {
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        Log.d(TAG,"onCreateView:$name")
        return null
    }

}