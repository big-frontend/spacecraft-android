package com.hawksjamesf.common.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ViewModelFactory private constructor()
    : ViewModelProvider.NewInstanceFactory() {


    companion object {
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        return ViewModelFactory()
                    }
                }
            }

            return INSTANCE

        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
        }
        throw IllegalArgumentException("unknown ViewModel class:${modelClass.name} ")
    }
}