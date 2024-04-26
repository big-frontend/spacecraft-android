package com.jamesfchen.myhome.page.newfeeds

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.paging.ExperimentalPagingApi
import com.jamesfchen.myhome.page.newfeeds.repository.CacheRegion
import com.jamesfchen.myhome.page.newfeeds.repository.ServiceLocator

class NewFeedsViewModel(val app: Application) : AndroidViewModel(app) {
    val newFeedsRepo = ServiceLocator.instance(app).getNewFeedsRepository(CacheRegion.IN_MEMORY_BY_PAGE)
    //    val allImages = flowOf(
//        repo.getAny()
//    ).flattenMerge(2)
    @OptIn(ExperimentalPagingApi::class)
    val newFeeds = newFeedsRepo.flow
    fun remove(uri: Uri) {
//        val list = allImages?.value
//        list?.remove(uri)
//        allImages?.postValue(list)
    }

    fun upload(uri: Uri) {

    }
}