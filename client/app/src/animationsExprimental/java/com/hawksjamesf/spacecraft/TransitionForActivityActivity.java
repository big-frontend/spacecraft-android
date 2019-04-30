package com.hawksjamesf.spacecraft;

import android.app.Activity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2018  Sun
 */
public class TransitionForActivityActivity extends Activity {
    private List<ViewModel> dataList = new ArrayList<ViewModel>() {
        {
            add(new ViewModel(R.drawable.tmp, "图片"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
        }
    };
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_for_activity);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(staggeredGridLayoutManager);
        Adapter adapter = new Adapter();
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        dividerItemDecoration2.setDrawable(getDrawable(R.drawable.divider));
        rv.addItemDecoration(dividerItemDecoration);
        rv.addItemDecoration(dividerItemDecoration2);
        Slide slide = new Slide(Gravity.LEFT);
        slide.setDuration(1000);
        slide.setInterpolator(new FastOutSlowInInterpolator());
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(slide);
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(TransitionForActivityActivity.this).inflate(R.layout.item_image_and_text, parent, false);
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width = ScreenUtils.getScreenWidth() / spanCount;
            itemView.setLayoutParams(layoutParams);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ViewModel viewModel = dataList.get(position);
            holder.iv.setImageResource(viewModel.drawableRes);
            holder.tv.setText(viewModel.text);

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailActivity.startActivity(TransitionForActivityActivity.this, iv, tv);
                }
            });
        }
    }

    class ViewModel {
        @DrawableRes
        int drawableRes;
        String text;

        public ViewModel(int drawableRes, String text) {
            this.drawableRes = drawableRes;
            this.text = text;
        }
    }
}
