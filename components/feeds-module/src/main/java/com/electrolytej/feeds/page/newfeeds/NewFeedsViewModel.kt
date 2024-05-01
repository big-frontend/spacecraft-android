package com.electrolytej.feeds.page.newfeeds

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.electrolytej.feeds.page.newfeeds.model.Item
import com.electrolytej.feeds.page.newfeeds.repository.CacheRegion
import com.electrolytej.feeds.page.newfeeds.repository.ServiceLocator
import kotlinx.coroutines.flow.Flow

class NewFeedsViewModel(val app: Application) : AndroidViewModel(app) {
    val newFeedsRepo = ServiceLocator.instance(app).getNewFeedsRepository(CacheRegion.IN_MEMORY_BY_PAGE)
    //    val allImages = flowOf(
//        repo.getAny()
//    ).flattenMerge(2)
    @OptIn(ExperimentalPagingApi::class)
    private val _newFeeds = newFeedsRepo.flow

    @OptIn(ExperimentalPagingApi::class)
    val newFeeds: Flow<PagingData<Item>> = _newFeeds
    fun remove(uri: Uri) {
//        val list = allImages?.value
//        list?.remove(uri)
//        allImages?.postValue(list)
    }

    fun upload(uri: Uri) {

    }
}