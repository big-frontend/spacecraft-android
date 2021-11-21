package com.jamesfchen.bundle2.customview.animationsExprimental;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.jamesfchen.bundle2.R;
import com.jamesfchen.bundle2.customview.Adapter;
import com.jamesfchen.bundle2.customview.ViewModel;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/25/2018  Sun
 * <p>
 * Activity的转场有两种：
 * - 窗口转场:窗口的转场将给窗口的进出场动画增加效果，比如百叶窗、幻灯片、渐变等效果。android系统提供的窗口过渡基本从侧边进入或者下面进入，并没有复杂的效果。
 * - 共享元素转场:常见于两个页面有共同的元素，比如图片，文件等。
 */
public class TransitionForActivityActivity extends Activity {
    public List<ViewModel> dataList = new ArrayList<ViewModel>() {
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

//    @AddTrace(name = "TransitionForActivityActivity_onCreate", enabled = true /* optional */)
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
        adapter.addDatas(dataList);
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
}
