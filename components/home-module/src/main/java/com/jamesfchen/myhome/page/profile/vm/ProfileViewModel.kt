package com.jamesfchen.myhome.page.profile.vm

import android.app.Application
import androidx.lifecycle.*
import com.jamesfchen.myhome.network.api.LocationApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/09/2019  Wed
 */
class ProfileViewModel(val app: Application) : AndroidViewModel(app) {
    private val _name = MutableLiveData("Ada")
    private val _lastName = MutableLiveData("Lovelace")
    private val _likes = MutableLiveData(0)

    val name2: LiveData<String> = _name
    val lastName2: LiveData<String> = _lastName
    val likes2: LiveData<Int> = _likes

    fun onLike2() {
        _likes.value = (_likes.value ?: 0) + 1
    }
    val locationDatas = flow {
        LocationApi.getDatas().forEach { l7 ->
            emit(l7)
            delay(1_00)
        }
    }.catch {
    }

    override fun onCleared() {
        super.onCleared()
        try {

        } finally {

        }
    }
}
