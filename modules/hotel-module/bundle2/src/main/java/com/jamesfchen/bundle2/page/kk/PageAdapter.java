package com.jamesfchen.bundle2.page.kk;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jamesfchen.bundle2.R;

import java.util.ArrayList;
import java.util.List;

import jamesfchen.widget.kk.PagerView;
import jamesfchen.widget.kk.TabsLayout;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: jamesfchen@gmail.com
 * @since: May/27/2019  Mon
 */
public class PageAdapter extends PagerView.Adapter<PageAdapter.ViewHolder> {
    private List<PagerViewModel> mDataList = new ArrayList<>();
    public boolean isEmpty(){
        return mDataList.isEmpty();
    }
    public void setDataList(List<PagerViewModel> dataList) {
        this.mDataList.clear();
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }
    public void removeAll(){
        this.mDataList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dump, parent, false)
        );
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        viewHolder.itemView.setLayoutParams(layoutParams);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvPosition.setText("Page position:" + position);
        position %= 5;
        if (position == 0) {
            holder.itemView.setBackgroundColor(Color.BLUE);
        } else if (position == 1) {
            holder.itemView.setBackgroundColor(Color.DKGRAY);
        } else if (position == 2) {
            holder.itemView.setBackgroundColor(Color.YELLOW);
        } else if (position == 3) {
            holder.itemView.setBackgroundColor(Color.RED);
        } else if (position == 4) {
            holder.itemView.setBackgroundColor(Color.CYAN);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public List<TabsLayout.TabItem> getTabList() {
        List<TabsLayout.TabItem> tabs = new ArrayList<TabsLayout.TabItem>();
        for (PagerViewModel pagerViewModel : mDataList) {
            tabs.add(pagerViewModel.getTab());
        }
        return tabs;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPosition;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_name);
        }
    }
}
