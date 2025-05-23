package com.electrolytej.bundle2.page.customview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electrolytej.bundle2.R;
import com.electrolytej.bundle2.page.customview.animationsExprimental.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: May/29/2021  Sat
 */
public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private List<ViewModel> dataList = new ArrayList<ViewModel>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_and_text, parent, false);
//            int spanCount = staggeredGridLayoutManager.getSpanCount();
//            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
//            layoutParams.width = ScreenUtils.getScreenWidth() / spanCount;
//            itemView.setLayoutParams(layoutParams);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewModel viewModel = dataList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int remain = position % 5;
                remain =-1;
                if (remain == 0) {
                    DetailActivity.startActivityWithSharedElement((Activity) holder.itemView.getContext(), holder.iv, holder.tv);
                } else if (remain == 1) {
//                        DetailActivity.startActivityWithCustom(this,R.animator.slide_in_up,R.animator.slide_out_down);
                    DetailActivity.startActivityWithCustom((Activity) holder.itemView.getContext(),android.R.anim.slide_in_left,android.R.anim.slide_in_left);
                } else if (remain == 2) {
                    DetailActivity.startActivityWithClipReveal((Activity) holder.itemView.getContext(),holder.iv,holder.iv.getWidth()/2,holder.iv.getHeight()/2,1000,1000);
                } else if (remain == 3) {
                    DetailActivity.startActivityWithScaleUp((Activity) holder.itemView.getContext(),holder.iv,holder.iv.getWidth()/2,holder.iv.getHeight()/2,200,200);
                } else if (remain == 4) {
                    DetailActivity.startActivityWithThumbnailScaleUp((Activity) holder.itemView.getContext(), holder.iv, holder.iv.getDrawingCache(), holder.iv.getWidth() / 2, holder.iv.getHeight() / 2);
                }else {
//                        DetailActivity.startActivityWithScaleUp((Activity) holder.itemView.getContext(),holder.itemView,0,0,holder.iv.getWidth(),holder.iv.getHeight());
                    DetailActivity.startActivityWithScene((Activity) holder.itemView.getContext(),holder.itemView,0,0,holder.iv.getWidth(),holder.iv.getHeight());
                }
            }
        });
        if (viewModel.drawableRes !=-1) holder.iv.setImageResource(viewModel.drawableRes);
        holder.tv.setText(viewModel.text+"=======>"+position);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addDatas(List<ViewModel> d){
        dataList.clear();
        dataList.addAll(d);
        notifyDataSetChanged();

    }
}