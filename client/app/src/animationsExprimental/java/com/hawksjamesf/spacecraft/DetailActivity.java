package com.hawksjamesf.spacecraft;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
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
 * @author: jf.chen
 * @email: jf.chen@Ctrip.com
 * @since: Apr/30/2019  Tue
 */
public class DetailActivity extends Activity {
    public static final String IV_TRANSITIONNAME = "image";
    public static final String TV_TRANSITIONNAME = "text";
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
    GridLayoutManager gridLayoutManager;
    ImageView ivCover;
    TextView tvText;

    public static void startActivity(Activity activity, ImageView iv, TextView tv) {
        Pair<View, String> pair0 = Pair.create((View) iv, IV_TRANSITIONNAME);
        Pair<View, String> pair1 = Pair.create((View) tv, TV_TRANSITIONNAME);
        ActivityCompat.startActivity(activity, new Intent(activity, DetailActivity.class),
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair0, pair1).toBundle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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
        Adapter adapter = new Adapter();
        rvContent.setAdapter(adapter);
        rvContent.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        rvContent.addItemDecoration(dividerItemDecoration);
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(DetailActivity.this).inflate(R.layout.item_detail_content, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ViewModel viewModel = dataList.get(position);
            holder.textview1.setText(viewModel.text);
            holder.textview2.setText(viewModel.text);
            holder.textview3.setText(viewModel.text);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview1;
        TextView textview2;
        TextView textview3;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textview1 = itemView.findViewById(R.id.textview1);
            textview2 = itemView.findViewById(R.id.textview2);
            textview3 = itemView.findViewById(R.id.textview3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    SnackbarUtil.with(tvText)
//                            .setActionText("action text")
//                            .setMessage("message")
//                            .setDuration(1000)
//                            .showSuccess();
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
