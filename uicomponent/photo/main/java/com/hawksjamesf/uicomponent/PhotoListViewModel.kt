package com.hawksjamesf.uicomponent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.hawksjamesf.uicomponent.repository.PhotoRepository

class PhotoListViewModel(
        val app: Application,
        private val repository: PhotoRepository) : AndroidViewModel(app) {
    val itemList: LiveData<PagedList<String>>? by lazy {
        repository.getAny()
    }
}