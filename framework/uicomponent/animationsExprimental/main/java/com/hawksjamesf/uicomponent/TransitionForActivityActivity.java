package com.hawksjamesf.uicomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.perf.metrics.AddTrace;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2018  Sun
 * <p>
 * Activity的转场有两种：
 * - 窗口转场:窗口的转场将给窗口的进出场动画增加效果，比如百叶窗、幻灯片、渐变等效果。android系统提供的窗口过渡基本从侧边进入或者下面进入，并没有复杂的效果。
 * - 共享元素转场:常见于两个页面有共同的元素，比如图片，文件等。
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
    RecyclerView rv;

    public static void startActivity(Activity activity) {
        ActivityCompat.startActivity(activity,
                new Intent(activity, TransitionForActivityActivity.class),
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @AddTrace(name = "_transitionForActivityActivity_onCreate", enabled = true /* optional */)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//        getWindow().setAllowEnterTransitionOverlap(false);
//        getWindow().setAllowReturnTransitionOverlap(false);

        setContentView(R.layout.activity_transition_for_activity);
        rv = (RecyclerView) findViewById(R.id.rv_image_text);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rv.setLayoutManager(staggeredGridLayoutManager);
        rv.setLayoutManager(linearLayoutManager);
        Adapter adapter = new Adapter();
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(TransitionForActivityActivity.this, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        rv.addItemDecoration(dividerItemDecoration);
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setDuration(1000);
        slide.setInterpolator(new FastOutSlowInInterpolator());
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        Transition slideRight = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
        getWindow().setEnterTransition(slideRight);

        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        findViewById(R.id.bt_PageSnap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagerSnapHelper.attachToRecyclerView(rv);
            }
        });
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        findViewById(R.id.bt_linearSnap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearSnapHelper.attachToRecyclerView(rv);
            }
        });


    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(TransitionForActivityActivity.this).inflate(R.layout.item_image_and_text, parent, false);
//            int spanCount = staggeredGridLayoutManager.getSpanCount();
//            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
//            layoutParams.width = ScreenUtils.getScreenWidth() / spanCount;
//            itemView.setLayoutParams(layoutParams);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            ViewModel viewModel = dataList.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int remain = position % 5;
                    remain =-1;
                    if (remain == 0) {
                        DetailActivity.startActivityWithSharedElement(TransitionForActivityActivity.this, holder.iv, holder.tv);
                    } else if (remain == 1) {
//                        DetailActivity.startActivityWithCustom(TransitionForActivityActivity.this,R.animator.slide_in_up,R.animator.slide_out_down);
                        DetailActivity.startActivityWithCustom(TransitionForActivityActivity.this,android.R.anim.slide_in_left,android.R.anim.slide_in_left);
                    } else if (remain == 2) {
                        DetailActivity.startActivityWithClipReveal(TransitionForActivityActivity.this,holder.iv,holder.iv.getWidth()/2,holder.iv.getHeight()/2,1000,1000);
                    } else if (remain == 3) {
                        DetailActivity.startActivityWithScaleUp(TransitionForActivityActivity.this,holder.iv,holder.iv.getWidth()/2,holder.iv.getHeight()/2,200,200);
                    } else if (remain == 4) {
                        DetailActivity.startActivityWithThumbnailScaleUp(TransitionForActivityActivity.this, holder.iv, holder.iv.getDrawingCache(), holder.iv.getWidth() / 2, holder.iv.getHeight() / 2);
                    }else {
//                        DetailActivity.startActivityWithScaleUp(TransitionForActivityActivity.this,holder.itemView,0,0,holder.iv.getWidth(),holder.iv.getHeight());
                        DetailActivity.startActivityWithScene(TransitionForActivityActivity.this,holder.itemView,0,0,holder.iv.getWidth(),holder.iv.getHeight());
                    }
                }
            });
            holder.iv.setImageResource(viewModel.drawableRes);
            holder.tv.setText(viewModel.text+"=======>"+position);

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
