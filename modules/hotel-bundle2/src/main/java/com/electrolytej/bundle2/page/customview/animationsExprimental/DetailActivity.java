package com.electrolytej.bundle2.page.customview.animationsExprimental;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.electrolytej.bundle2.R;
import com.electrolytej.bundle2.page.customview.animationsExprimental.transitions.ScaleUpTransition;
import com.electrolytej.bundle2.page.customview.animationsExprimental.transitions.ScaleUpTransitionv2;
import com.electrolytej.util.CollectionUtil;
import com.electrolytej.widget.recyclerview.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class DetailActivity extends Activity {
    public static final String IV_TRANSITIONNAME = "image";
    public static final String TV_TRANSITIONNAME = "text";
    public static final String SCALEUP_TRANSITIONNAME = "scale_up";

    GridLayoutManager gridLayoutManager;
    ImageView ivCover;
    TextView tvText;

    public static void startActivityWithSharedElement(Activity activity, ImageView iv, TextView tv) {
        Pair<View, String> pair0 = Pair.create((View) iv, IV_TRANSITIONNAME);
        Pair<View, String> pair1 = Pair.create((View) tv, TV_TRANSITIONNAME);
        ActivityCompat.startActivity(activity, new Intent(activity, DetailActivity.class), ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair0, pair1).toBundle());
    }

    public static void startActivityWithScene(Activity activity, View itemView, int startX, int startY, int startWidth, int startHeight) {
        Pair<View, String> pair0 = Pair.create((View) itemView, SCALEUP_TRANSITIONNAME);
        ActivityCompat.startActivity(activity, new Intent(activity, DetailActivity.class), ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair0).toBundle());
    }

    public static void startActivityWithCustom(Activity activity, int enterResId, int exitResId) {
        ActivityCompat.startActivity(activity, new Intent(activity, DetailActivity.class), ActivityOptionsCompat.makeCustomAnimation(activity, enterResId, exitResId).toBundle());
    }

    public static void startActivityWithClipReveal(Activity activity, View source, int startX, int startY, int startWidth, int startHeight) {
        ActivityCompat.startActivity(activity, new Intent(activity, DetailActivity.class), ActivityOptionsCompat.makeClipRevealAnimation(source, startX, startY, startWidth, startHeight).toBundle());

    }

    public static void startActivityWithScaleUp(Activity activity, View source, int startX, int startY, int startWidth, int startHeight) {
        ActivityCompat.startActivity(activity, new Intent(activity, DetailActivity.class), ActivityOptionsCompat.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight).toBundle());

    }

    public static void startActivityWithThumbnailScaleUp(Activity activity, View source, Bitmap thumbnail, int startX, int startY) {
        ActivityCompat.startActivity(activity, new Intent(activity, DetailActivity.class), ActivityOptionsCompat.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY).toBundle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ivCover = findViewById(R.id.iv_cover);
        tvText = findViewById(R.id.tv_text);
        ivCover.setTransitionName(IV_TRANSITIONNAME);
        tvText.setTransitionName(TV_TRANSITIONNAME);

//        postponeEnterTransition();
//        iv.getViewTreeObserver().addOnPreDrawListener(
//                new ViewTreeObserver.OnPreDrawListener() {
//                    @Override
//                    public boolean onPreDraw() {
//                        iv.getViewTreeObserver().removeOnPreDrawListener(this);
//                        startPostponedEnterTransition();
//                        return true;
//                    }
//                }
//        );

//        getWindow().setTransitionBackgroundFadeDuration(2000);
        Transition sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();
        Transition sharedElementExitTransition = getWindow().getSharedElementExitTransition();
        Transition sharedElementReenterTransition = getWindow().getSharedElementReenterTransition();
        Transition sharedElementReturnTransition = getWindow().getSharedElementReturnTransition();
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                super.onRejectSharedElements(rejectedSharedElements);
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
            }

            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            }

            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                return super.onCreateSnapshotView(context, snapshot);
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
            }
        });

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                super.onRejectSharedElements(rejectedSharedElements);
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
            }

            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            }

            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                return super.onCreateSnapshotView(context, snapshot);
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
            }
        });

        RecyclerView rvContent = (RecyclerView) findViewById(R.id.rv_content);
        gridLayoutManager = new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position > 4 || position == 0) ? 4 : position;
            }
        });
        rvContent.setLayoutManager(gridLayoutManager);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(R.layout.item_detail_content,
                new int[]{R.id.textview1, R.id.textview2, R.id.textview3},
                CollectionUtil.list("图片1", "图片2", "图片3", "图片4", "图片5", "图片6")
        );
        rvContent.setAdapter(adapter);
        rvContent.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        rvContent.addItemDecoration(dividerItemDecoration);

        LinearLayout llrootVIew = findViewById(R.id.ll_rootview);
        llrootVIew.setVisibility(View.GONE);
        llrootVIew.setTransitionName(SCALEUP_TRANSITIONNAME);
        Transition scaleUpTransition = new ScaleUpTransition();
//        getWindow().setEnterTransition(scaleUpTransition);
//        getWindow().setReturnTransition(scaleUpTransition);
        Transition scaleUpTransitionv2 = new ScaleUpTransitionv2();
        getWindow().setSharedElementEnterTransition(scaleUpTransitionv2);
        getWindow().setSharedElementReturnTransition(scaleUpTransitionv2);
//        Transition scaleup = TransitionInflater.from(this).inflateTransition(R.transition.enter_share_element_detail);
        Transition scaleup = new ChangeBounds();
        scaleup.excludeTarget(android.R.id.statusBarBackground, true);
        scaleup.addTarget(llrootVIew);
//        getWindow().setSharedElementEnterTransition(scaleup);
//        getWindow().setSharedElementReturnTransition(scaleup);
        llrootVIew.setVisibility(View.VISIBLE);
    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        ActivityCompat.finishAfterTransition(this);
//    }

}
