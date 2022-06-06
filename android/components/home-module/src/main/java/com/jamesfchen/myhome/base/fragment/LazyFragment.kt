package com.jamesfchen.myhome.base.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes

/**
 * ViewPager的Fragment懒加载
 */
abstract class LazyFragment : Fragment() {
    private var isViewCreated = false
    private var isVisibleToUser = false
    private var isDataInitiated = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareLoadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isViewCreated = false
        return inflater.inflate(getLayoutId(), container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
    }

    override fun onActivityCreated(saveInstanceState: Bundle?) {
        super.onActivityCreated(saveInstanceState)
        prepareLoadData()
    }

    private fun prepareLoadData() {
        if (isVisibleToUser && isViewCreated && !isDataInitiated) {
            isDataInitiated = true
            loadData()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun loadData()
}