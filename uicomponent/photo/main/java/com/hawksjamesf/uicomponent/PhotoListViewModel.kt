package com.hawksjamesf.uicomponent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList

class PhotoListViewModel(app: Application) : AndroidViewModel(app) {
     val itemList: MutableLiveData<PagedList<String>> =MutableLiveData()

}