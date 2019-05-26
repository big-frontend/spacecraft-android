package com.hawksjamesf.spacecraft;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class GenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Integer> dataList = new ArrayList<Integer>() {{
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(6);
        add(7);
        add(8);
        add(9);
        add(10);
    }};

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_generic, parent, false)) {

        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextView) holder.itemView.findViewById(R.id.tv)).setText("position:" + position);
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

    public void setDataList(List<Integer> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
}
