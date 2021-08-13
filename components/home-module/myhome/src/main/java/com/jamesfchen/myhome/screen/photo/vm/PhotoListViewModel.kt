package com.jamesfchen.myhome.screen.photo.vm

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.jamesfchen.myhome.screen.photo.repository.PhotoRepository
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf

class PhotoListViewModel(
        val app: Application,
        val repo: PhotoRepository
) : AndroidViewModel(app) {
//    val allImages = flowOf(
//        repo.getAny()
//    ).flattenMerge(2)
val allImages =repo.getAny()
    fun remove(uri: Uri) {
//        val list = allImages?.value
//        list?.remove(uri)
//        allImages?.postValue(list)
    }

    fun upload(uri: Uri) {

    }
}