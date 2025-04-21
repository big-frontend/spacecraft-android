package com.electrolytej.feeds.page.newfeeds.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.electrolytej.feeds.network.api.NewFeedsApi
import com.electrolytej.feeds.page.newfeeds.model.Item

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/24/2019  Sun
 */
class PhotoItemKeyedDataSource(val api: NewFeedsApi) : PagingSource<String, Item>() {


    override suspend fun load(params: LoadParams<String>): LoadResult<String, Item> {
        return LoadResult.Page(mutableListOf(),null,null)
    }

    override fun getRefreshKey(state: PagingState<String, Item>): String? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.nextKey }
    }
}