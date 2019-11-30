package com.hawksjamesf.uicomponent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.hawksjamesf.uicomponent.repository.PhotoRepository

class PhotoListViewModel(
        val app: Application,
        private val repository: PhotoRepository) : AndroidViewModel(app) {
    var itemList: LiveData<PagedList<String>>? = null

    fun getAny(): LiveData<PagedList<String>>? {
        itemList = repository.getAny()
        return itemList
    }

}