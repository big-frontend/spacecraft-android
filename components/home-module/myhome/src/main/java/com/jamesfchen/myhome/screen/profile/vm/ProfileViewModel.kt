package com.jamesfchen.myhome.screen.profile.vm

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jamesfchen.mvvm.ObservableViewModel
import com.jamesfchen.myhome.model.L7
import com.jamesfchen.myhome.network.api.LocationApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/09/2019  Wed
 */
class ProfileViewModel : ViewModel() {
    private val _name = MutableLiveData("Ada")
    private val _lastName = MutableLiveData("Lovelace")
    private val _likes =  MutableLiveData(0)

    val name2: LiveData<String> = _name
    val lastName2: LiveData<String> = _lastName
    val likes2: LiveData<Int> = _likes

    fun onLike2() {
        _likes.value = (_likes.value ?: 0) + 1
    }

    val locationDatas = flow<L7> {
        val datas = LocationApi.getDatas()
        for (i in 0..2){
            emit(datas[i])
        }
    }
    val ll= flowOf(1,2,3,4,5,6)
    override fun onCleared() {
        super.onCleared()
        try {

        }finally {

        }
    }
}
