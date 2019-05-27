package com.hawksjamesf.common.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hawksjamesf.common.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: May/27/2019  Mon
 */
public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder1> {
    private List<Integer> mDataList = new ArrayList<Integer>() {{
        for (int i = 0; i < 5; ++i) add(i);
    }};

    public void setDataList(List<Integer> dataList) {
        this.mDataList.clear();
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    private Adapter2 mAdapter2 = new Adapter2();

    public void insertAdapter(Adapter2 adapter) {
        mAdapter2 = adapter;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder1 viewHolder1 = new ViewHolder1(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nest, parent, false)
        );
        viewHolder1.rvNest.setAdapter(mAdapter2);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        Adapter2 adapter = (Adapter2) holder.rvNest.getAdapter();
        if (adapter != null) {
            adapter.setDataList(mDataList);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        RecyclerView rvNest;

        ViewHolder1(@NonNull View itemView) {
            super(itemView);
            rvNest = itemView.findViewById(R.id.rv_nest);
            rvNest.setLayoutManager(new LinearLayoutManager(rvNest.getContext(), RecyclerView.HORIZONTAL, false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvNest.getContext(), RecyclerView.HORIZONTAL);
            Drawable drawable = itemView.getContext().getDrawable(R.drawable.divider);
            if (drawable != null) {
                dividerItemDecoration.setDrawable(drawable);
            }
            rvNest.addItemDecoration(dividerItemDecoration);

        }
    }
}
