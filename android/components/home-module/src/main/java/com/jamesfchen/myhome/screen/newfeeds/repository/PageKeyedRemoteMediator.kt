package com.jamesfchen.myhome.screen.newfeeds.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.jamesfchen.myhome.screen.newfeeds.model.Item

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(val api:NetworkApi) : RemoteMediator<String, Item>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<String, Item>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}