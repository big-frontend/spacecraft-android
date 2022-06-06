package com.jamesfchen.myhome.screen.photo.vm

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.jamesfchen.myhome.screen.photo.repository.PhotoRepository

class PhotoListViewModel(
    val app: Application,
    repo: PhotoRepository
) : AndroidViewModel(app) {
    //    val allImages = flowOf(
//        repo.getAny()
//    ).flattenMerge(2)
    val allImages = repo.flow
    fun remove(uri: Uri) {
//        val list = allImages?.value
//        list?.remove(uri)
//        allImages?.postValue(list)
    }

    fun upload(uri: Uri) {

    }
}