package com.jamesfchen.uicomponent.repository

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.jamesfchen.uicomponent.model.Item

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/24/2019  Sun
 */
class PhotoItemKeyedDataSource(api: NetworkApi) : ItemKeyedDataSource<String, Item>() {
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Item>) {
        Log.d("hawks", "PhotoItemKeyedDataSource:loadInitial:")
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Item>) {
        Log.d("hawks", "PhotoItemKeyedDataSource:loadAfter:")
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Item>) {
        Log.d("hawks", "PhotoItemKeyedDataSource:loadBefore:")
    }

    override fun getKey(item: Item): String {
        Log.d("hawks", "PhotoItemKeyedDataSource:getKey:")
        return ""
    }
}