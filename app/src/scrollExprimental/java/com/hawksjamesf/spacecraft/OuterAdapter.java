package com.hawksjamesf.spacecraft;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * @since: May/25/2019  Sat
 */
public class OuterAdapter extends RecyclerView.Adapter<OuterAdapter.ViewHolder> {
    List<Integer> dataList = new ArrayList<Integer>() {{
        for (int i = 0; i < 4; ++i) add(i);
    }};


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nest, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((GenericAdapter) holder.rvNest.getAdapter()).setDataList(dataList);
        holder.tvPagerName.setText("pager:" + position);
        holder.tvPagerName.setBackgroundColor(Color.CYAN);
        holder.tvPagerName.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rvNest;
        private TextView tvPagerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvNest = itemView.findViewById(R.id.rv_nest);
            GenericAdapter genericAdapter = new GenericAdapter();
            rvNest.setAdapter(genericAdapter);
            rvNest.setLayoutManager(new LinearLayoutManager(rvNest.getContext(), RecyclerView.HORIZONTAL, false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvNest.getContext(), RecyclerView.HORIZONTAL);
            dividerItemDecoration.setDrawable(itemView.getContext().getDrawable(R.drawable.divider));
            rvNest.addItemDecoration(dividerItemDecoration);
//            holder.itemView.setBackground();
            tvPagerName = itemView.findViewById(R.id.tv_pager_name);
        }

    }

}
