package com.jamesfchen.loader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Feb/07/2022  Mon
 * issue：当使用ViewPager的setOffscreenPageLimit时，
 * 如果没有控制处于后台fragment加载数据的过程（后台fragment在loadData过程中，时常会弹出一些网络请求问题的dialog，而dialog需要fragment在前台显示），
 * 那么就总会出现Bad window token, you cannot show a dialog before an Activity is created or after it's hidden的问题。
 * 所以fragment在loadData时，需要时fragment处于前台可见状态。
 */
public abstract class LazyLoadDataFragment extends Fragment {
    boolean isViewInitiated;
    boolean isVisibleToUser;
    boolean isDataInitiated;

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareLoadData();
    }
    @Override
    public View onCreateView(LayoutInflater inflate, ViewGroup container, Bundle savedInstanceState) {
        isViewInitiated = false;
        return initView();
    }

    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        isViewInitiated = true;
        createHandler();
        prepareLoadData();
    }

    public void prepareLoadData() {
        if (isVisibleToUser && isViewInitiated && !isDataInitiated) {
            isDataInitiated = true;
            loadData();
        }
    }

    public abstract View initView();

    public abstract void createHandler();

    public abstract void loadData();
}