package com.jamesfchen.bundle2.kk;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jamesfchen.bundle2.R;
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
public class Adapter1 extends PagerView.Adapter<Adapter1.ViewHolder1> {
    private List<PagerViewModel> mDataList = new ArrayList<PagerViewModel>();

    public void setDataList(List<PagerViewModel> dataList) {
        this.mDataList.clear();
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder1 viewHolder1 = new ViewHolder1(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nest, parent, false)
        );
        viewHolder1.rvNest.setAdapter(new Adapter11());
//        pv?.addOnTabSelectedListener(new  TabsLayout.OnTabSelectedListener() {
//            override fun onTabSelected(view: View?, position: Int) {
//                viewHolder1.rvNest.scrollToPosition(0)
//            }
//        })
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        Adapter11 adapter = (Adapter11) holder.rvNest.getAdapter();
        if (adapter != null) {
            adapter.setDataList(mDataList.get(position).getContents());
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
