package com.electrolytej.feeds.page.newfeeds.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.electrolytej.feeds.network.api.NewFeedsApi
import com.electrolytej.feeds.page.newfeeds.model.Item

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(val api: NewFeedsApi) : RemoteMediator<String, Item>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<String, Item>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}