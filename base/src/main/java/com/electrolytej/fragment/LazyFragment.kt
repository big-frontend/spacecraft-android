package com.electrolytej.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes

/**
 * ViewPager的Fragment懒加载
 *
 * issue：当使用ViewPager的setOffscreenPageLimit时，
 * 如果没有控制处于后台fragment加载数据的过程（后台fragment在loadData过程中，时常会弹出一些网络请求问题的dialog，而dialog需要fragment在前台显示），
 * 那么就总会出现Bad window token, you cannot show a dialog before an Activity is created or after it's hidden的问题。
 * 所以fragment在loadData时，需要时fragment处于前台可见状态。
 */
abstract class LazyFragment : Fragment() {
    private var isViewCreated = false
    private var isDataInitiated = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
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
        if (userVisibleHint && isViewCreated && !isDataInitiated) {
            isDataInitiated = true
            loadData()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun loadData()
}