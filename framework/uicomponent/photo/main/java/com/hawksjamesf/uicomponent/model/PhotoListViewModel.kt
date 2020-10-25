package com.hawksjamesf.uicomponent.model

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.hawksjamesf.uicomponent.repository.PhotoRepository

class PhotoListViewModel(
        val app: Application,
        val repo: PhotoRepository
) : AndroidViewModel(app) {
    val allImages: LiveData<PagedList<Item>>? by lazy {
        repo.getAny()
    }

    fun remove(uri: Uri) {
//        val list = allImages?.value
//        list?.remove(uri)
//        allImages?.postValue(list)
    }

    fun upload(uri: Uri) {

    }
}