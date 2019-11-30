package com.hawksjamesf.uicomponent.repository

import android.util.Log
import androidx.paging.ItemKeyedDataSource

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/24/2019  Sun
 */
class PhotoItemKeyedDataSource(api: NetworkApi) : ItemKeyedDataSource<String, String>() {
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String>) {
        Log.d("hawks", "PhotoItemKeyedDataSource:loadInitial:")
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String>) {
        Log.d("hawks", "PhotoItemKeyedDataSource:loadAfter:")
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String>) {
        Log.d("hawks", "PhotoItemKeyedDataSource:loadBefore:")
    }

    override fun getKey(item: String): String {
        Log.d("hawks", "PhotoItemKeyedDataSource:getKey:")
        return ""
    }
}