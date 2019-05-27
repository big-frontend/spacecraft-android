package com.hawksjamesf.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hawksjamesf.common.adapter.Adapter1;
import com.hawksjamesf.common.adapter.Adapter2;

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
    List<String> tabList = new ArrayList<String>() {
        {
            add("美食");
            add("美食");
            add("美食");
            add("美食");
            add("美食");
        }
    };


    private RecyclerView mRvContent;
    private LinearLayoutManager mLinearLayoutManager;
    private int mOrientation = RecyclerView.HORIZONTAL;
    private SnapHelper mSnapHelper;
    private Adapter1 mAdapter1 = new Adapter1();

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
        mRvContent.setAdapter(mAdapter1);

    }


    public void setAdapter1(@NonNull Adapter1 adapter) {
        mAdapter1 = adapter;
    }

    public void setAdapter2(@NonNull Adapter2 adapter) {
        mAdapter1.insertAdapter(adapter);
    }


    public abstract static class Adapter<T extends ViewHolder> extends RecyclerView.Adapter<T> {
        @NonNull
        public abstract T onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

        public abstract void onBindViewHolder(@NonNull T holder, int position);

        public abstract int getItemCount();
    }


    public abstract static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
