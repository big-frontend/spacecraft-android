package com.hawksjamesf.common.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hawksjamesf.common.R;

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
public class Adapter22 extends RecyclerView.Adapter<Adapter22.ViewHolder2> {
    private List<Object> mDataList = new ArrayList<Object>();

    public void setDataList(List<?> dataList) {
        this.mDataList.clear();
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder2(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dump, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {
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


    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView tvPosition;

        ViewHolder2(@NonNull View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_name);

        }
    }

}
