package com.hawksjamesf.common.mvp

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/11/2018  Sun
 */
abstract class AbstractSpacecraftPresenter<V> : SpacecraftPresenter {
    protected var v: V? = null
    fun onAttach(v: V) {
        this.v = v
    }

    fun onDetach() {
        this.v = null
    }
}