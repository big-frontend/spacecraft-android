package com.hawksjamesf.uicomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
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
 *
 * 实现了嵌套RecyclerView+RecyclerView同方向的滑动冲突
 */
public class PagerView extends LinearLayout {
    private RecyclerView mRvContent;
    private TabsLayout mTabsLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private SnapHelper mSnapHelper;
    private int mOrientation;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerView, defStyleAttr, defStyleRes);
        mOrientation = typedArray.getInteger(R.styleable.PagerView_pv_orientation, HORIZONTAL);
        typedArray.recycle();

        View rootView = inflate(context, R.layout.view_pager, this);
        mRvContent = rootView.findViewById(R.id.rv_content);
        mTabsLayout = rootView.findViewById(R.id.tl_tabs);
        mTabsLayout.setOrientation(mOrientation);
        mLinearLayoutManager = new LinearLayoutManager(context, mOrientation, false);
        if (mOrientation == HORIZONTAL) {
            mSnapHelper = new PagerSnapHelper();
            mSnapHelper.attachToRecyclerView(mRvContent);
        }
        mRvContent.setLayoutManager(mLinearLayoutManager);
        mTabsLayout.addOnTabSelectedListener(new TabsLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(View view, int position) {
                if (mOnTabSelectedListenerList == null) return;
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
