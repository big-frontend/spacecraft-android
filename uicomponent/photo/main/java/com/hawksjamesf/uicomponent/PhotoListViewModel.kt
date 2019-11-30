package com.hawksjamesf.uicomponent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.hawksjamesf.uicomponent.repository.PhotoRepository

class PhotoListViewModel(
        val app: Application,
        private val repository: PhotoRepository) : AndroidViewModel(app) {
    val itemList: MutableLiveData<PagedList<String>> = MutableLiveData()

    fun getAny(): LiveData<PagedList<String>>?{
       return repository.getAny()
    }

}