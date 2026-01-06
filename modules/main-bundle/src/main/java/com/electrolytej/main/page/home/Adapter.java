package com.electrolytej.main.page.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.electrolytej.main.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Object> mDataList = new ArrayList<Object>();

    public boolean isEmpty() {
        return mDataList.isEmpty();
    }

    public void setDataList(List<?> dataList) {
        this.mDataList.clear();
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.mDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dump, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvPosition.setText("position:" + position);
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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPosition;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_name);
        }
    }

}
