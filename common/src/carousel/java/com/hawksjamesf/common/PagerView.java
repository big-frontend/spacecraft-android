package com.hawksjamesf.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class PagerView extends LinearLayout {
    private RecyclerView mRvContent;
    private TabsLayout mTabsLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private int mOrientation = RecyclerView.HORIZONTAL;
    private SnapHelper mSnapHelper;

    public PagerView(@NonNull Context context) {
        this(context, null);

    }

    public PagerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PagerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public PagerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View rootView = inflate(context, R.layout.view_pager, this);

        mRvContent = rootView.findViewById(R.id.rv_content);
        if (mOrientation == RecyclerView.HORIZONTAL) {
            mLinearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            mSnapHelper = new PagerSnapHelper();
        } else {
            mLinearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            mSnapHelper = new LinearSnapHelper();

        }
        mSnapHelper.attachToRecyclerView(mRvContent);
        mRvContent.setLayoutManager(mLinearLayoutManager);

        mTabsLayout = rootView.findViewById(R.id.tl_tabs);
        mTabsLayout.addOnTabSelectedListener(new TabsLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(View view, int position) {
                for (TabsLayout.OnTabSelectedListener l : mOnTabSelectedListenerList) {
                    if (l != null) {
                        l.onTabSelected(view, position);
                    }
                }
            }
        });

    }

    private List<TabsLayout.OnTabSelectedListener> mOnTabSelectedListenerList;

    public void addOnTabSelectedListener(TabsLayout.OnTabSelectedListener onTabSelectedListener) {
        if (mOnTabSelectedListenerList == null) {
            mOnTabSelectedListenerList = new ArrayList<>();
        }
        mOnTabSelectedListenerList.add(onTabSelectedListener);
    }

    public void setAdapter(@NonNull final Adapter adapter) {
        mRvContent.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                mTabsLayout.setDataList(adapter.getTabList());
            }
        });
    }

    public static abstract class Adapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
        public abstract List<TabsLayout.TabItem> getTabList();

    }
}
