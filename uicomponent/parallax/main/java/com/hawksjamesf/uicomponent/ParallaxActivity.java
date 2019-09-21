package com.hawksjamesf.uicomponent;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hawksjamesf.common.util.ConvertUtil;
import com.hawksjamesf.uicomponent.widget.ChaplinVideoView;
import com.hawksjamesf.uicomponent.widget.Constants;
import com.tencent.bugly.crashreport.CrashReport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® 2019
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/05/2019  Tue
 */
public class ParallaxActivity extends AppCompatActivity {
    LinearLayout item_top_bar_for_recyclerview;
    LinearLayout item_top_bar_for_listview;
    ChaplinVideoView clvForRecyclerView;
    ChaplinVideoView clvForListView;
    RecyclerView rv;
    CustListView lv;
    private int mTouchMoveCount = 0;
    private float mStartY = 0;
    private long mStartEventTime = 0;
    private final int MIN_FRAME_TIME = 16;
    private final int MAX_MOVE_COUNT = 6;
    private int mOffsetY = ConvertUtil.dp2px(200);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);

//        CustClipView ccv = findViewById(R.id.ccv);
//        View view = View.inflate(this, R.layout.view_cust_clip, ccv);


        item_top_bar_for_recyclerview = findViewById(R.id.item_top_bar_for_recyclerview);
        clvForRecyclerView = item_top_bar_for_recyclerview.findViewById(R.id.clv);
        clvForRecyclerView.setDataSourceAndPlay(Uri.parse(Constants.VIDEO_URL))
//        clvForRecyclerView.setDataSourceAndPlay(R.raw.wechatsight1)
                .bindLifecycle(getLifecycle());
        item_top_bar_for_listview = findViewById(R.id.item_top_bar_for_listview);
        clvForListView = item_top_bar_for_listview.findViewById(R.id.clv);

        rv = findViewById(R.id.rv);
        final MyAdapter myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rv.getContext(), RecyclerView.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
//        final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
//        pagerSnapHelper.attachToRecyclerView(rv);
//        rv.addItemDecoration(new );
        rv.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {


            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    int lastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    Log.d("onScrollStateChanged", "firstVisibleItemPosition/lastVisibleItemPosition:" + firstVisibleItemPosition + "/" + lastVisibleItemPosition
                            + "--->firstCompletelyVisibleItemPosition/lastCompletelyVisibleItemPosition" + firstCompletelyVisibleItemPosition + "/" + lastCompletelyVisibleItemPosition);

                    RecyclerView.ViewHolder viewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition);
                    if (viewHolderForAdapterPosition != null) {
                        int oldPosition = viewHolderForAdapterPosition.getOldPosition();
                        int adapterPosition = viewHolderForAdapterPosition.getAdapterPosition();
                        int layoutPosition = viewHolderForAdapterPosition.getLayoutPosition();
                        Log.d("onScrollStateChanged", "viewHolderForAdapterPosition--->oldPosition/adapterPosition/layoutPosition:" + oldPosition + "/" + adapterPosition + "/" + layoutPosition);
                    }
                    RecyclerView.ViewHolder viewHolderForLayoutPosition = recyclerView.findViewHolderForLayoutPosition(firstVisibleItemPosition);

                    if (viewHolderForLayoutPosition != null) {
                        int oldPosition = viewHolderForLayoutPosition.getOldPosition();
                        int adapterPosition = viewHolderForLayoutPosition.getAdapterPosition();
                        int layoutPosition = viewHolderForLayoutPosition.getLayoutPosition();
                        Log.d("onScrollStateChanged", "viewHolderForLayoutPosition--->oldPosition/adapterPosition/layoutPosition:" + oldPosition + "/" + adapterPosition + "/" + layoutPosition);
//                        ((MyViewHolder) viewHolderForLayoutPosition).clv.start();
                    }
                }else if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    Log.d("onScrollStateChanged", "SCROLL_STATE_DRAGGING");
                    int childCount = recyclerView.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                    RecyclerView.ViewHolder viewHolderForLayoutPosition = recyclerView.findViewHolderForAdapterPosition(i);

                    if (viewHolderForLayoutPosition != null) {
//                    ((MyViewHolder) viewHolderForLayoutPosition).clv.start();
                    }

                    }

                    CrashReport.setUserSceneTag(ParallaxActivity.this, 111685); // 上报后的Crash会显示该标签
                    CrashReport.testJavaCrash();
                }
            }


            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

        });
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mStartY == 0) {
                    mStartY = event.getY();
                }

                if (mStartEventTime == 0) {
                    mStartEventTime = event.getEventTime();
                }

                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    return false;
                } else if (action == MotionEvent.ACTION_MOVE) {
                    long current = event.getEventTime();
                    if (current - mStartEventTime < MIN_FRAME_TIME) {
                        return false;
                    }
                    mTouchMoveCount += 1;
                    if (mTouchMoveCount > MAX_MOVE_COUNT) {
                        return false;
                    }
                    moveUpAndDown(v, event);
                    return false;
                } else if (action == MotionEvent.ACTION_UP) {
                    mStartY = 0;
                    mStartEventTime = 0;
                    mTouchMoveCount = 0;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    mStartY = 0;
                    mStartEventTime = 0;
                    mTouchMoveCount = 0;
                }
                return false;
            }
        });

        lv = findViewById(R.id.lv);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 23;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Log.d("ParallaxActivity", "ListView:getView:position/childCount: " + position+"/"+parent.getChildCount());
                MyViewHolder myViewHolder;
                if (convertView == null) {
                    View itemView = LayoutInflater.from(ParallaxActivity.this).inflate(R.layout.item_my, parent, false);
                    myViewHolder = new MyViewHolder(itemView);
                    itemView.setTag(myViewHolder);
                    convertView = itemView;

                } else {
                    myViewHolder = (MyViewHolder) convertView.getTag();
                }
                try {
                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myViewHolder.tvText.setText("ListView:position:" + position);
                return convertView;

            }
        });
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mStartY == 0) {
                    mStartY = event.getY();
                }

                if (mStartEventTime == 0) {
                    mStartEventTime = event.getEventTime();
                }

                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    return false;
                } else if (action == MotionEvent.ACTION_MOVE) {
                    long current = event.getEventTime();
                    if (current - mStartEventTime < MIN_FRAME_TIME) {
                        return false;
                    }
                    mTouchMoveCount += 1;
                    if (mTouchMoveCount > MAX_MOVE_COUNT) {
                        return false;
                    }
                    moveUpAndDown(v, event);
                    return false;
                } else if (action == MotionEvent.ACTION_UP) {
                    mStartY = 0;
                    mStartEventTime = 0;
                    mTouchMoveCount = 0;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    mStartY = 0;
                    mStartEventTime = 0;
                    mTouchMoveCount = 0;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void moveUpAndDown(View v, MotionEvent event) {
        float currentY = event.getY();
        int deltaY = (int) (currentY - mStartY);
        int slop = ViewConfiguration.get(v.getContext()).getScaledTouchSlop() * 2;
        if (Math.abs(deltaY) > slop) {
            if (deltaY > 0) {
                //向下滑动
                startTitleViewAnimator(v, 0);
            } else {
                //向上滑动
                startTitleViewAnimator(v, -mOffsetY);
            }
        }
    }


    private ValueAnimator mObjectAnimator;
    private int mDuration = 3000;

    private void startTitleViewAnimator(final View v, final int endPaddingTop) {
        if (item_top_bar_for_recyclerview != null || item_top_bar_for_listview != null) {
            int start = 0;
            if (v instanceof RecyclerView) {
                start = item_top_bar_for_recyclerview.getPaddingTop();
            } else if (v instanceof ListView) {
                start = item_top_bar_for_listview.getPaddingTop();

            }
            final int end = endPaddingTop;
            Log.d("ParallaxActivity", "section:" + start + "/" + end);
            if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
                mObjectAnimator.cancel();
                mObjectAnimator.removeAllListeners();
            }

            mObjectAnimator = ValueAnimator.ofInt(start, end);
            mObjectAnimator.setDuration(mDuration);
            mObjectAnimator.setInterpolator(new DecelerateInterpolator());
            mObjectAnimator.start();
            mObjectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
//                    lv.aminating = true;
                }
            });
            mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Integer animatedValue = (Integer) valueAnimator.getAnimatedValue();
                    int paddingLeft = 0;
                    int paddingRight = 0;
                    int paddingBottom = 0;
                    if (v instanceof RecyclerView) {
                        paddingLeft = item_top_bar_for_recyclerview.getPaddingLeft();
                        paddingRight = item_top_bar_for_recyclerview.getPaddingRight();
                        paddingBottom = item_top_bar_for_recyclerview.getPaddingBottom();
                        item_top_bar_for_recyclerview.setPadding(paddingLeft, animatedValue, paddingRight, paddingBottom);
                    } else if (v instanceof ListView) {
                        paddingLeft = item_top_bar_for_listview.getPaddingLeft();
                        paddingRight = item_top_bar_for_listview.getPaddingRight();
                        paddingBottom = item_top_bar_for_listview.getPaddingBottom();

                        item_top_bar_for_listview.setPadding(paddingLeft, animatedValue, paddingRight, paddingBottom);
//                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lv.getLayoutParams();
//                        layoutParams.height += -animatedValue;
//                        lv.setLayoutParams(layoutParams);
//                        item_top_bar_for_listview.offsetTopAndBottom(animatedValue);
//                        lv.offsetTopAndBottom(animatedValue);
                            lv.aminating = true;
                        //下滑 animatedValue =0；上滑 animatedValue = -528
                            if (animatedValue == -mOffsetY) {//上滑
                                        lv.moveUp =true;
                            } else if (animatedValue == 0) {//下滑
                                        lv.moveUp =false;

                            }
                        }

                }
            });
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        public static final int FIRST_BOOT = 0;
        public static final int NON_FIRST_BOOT = 1;

        @Override
        public int getItemViewType(int position) {
            int viewtype;
            if (position == 0) {
                viewtype = FIRST_BOOT;
            } else {
                viewtype = NON_FIRST_BOOT;
            }

            return viewtype;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("ParallaxActivity", "onCreateViewHolder");
            MyViewHolder viewHolder;
            if (FIRST_BOOT == viewType) {
                viewHolder = new MyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false),
                        true
                );
            } else {
                viewHolder = new MyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false)
                );
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Log.d("ParallaxActivity", "RecyclerView:onBindViewHolder:position:" + position);
            holder.tvText.setText("RecyclerView:position:" + position);
            holder.clv.setPosition(position);
            if (holder.isFirstBoot && position == 0) {
                holder.clv.setDataSourceAndPlay(R.raw.wechatsight1)
//                        .bindLifecycle(ParallaxActivity.this.getLifecycle())
                ;
                holder.isFirstBoot = false;
            } else if (position % 2 == 0) {
//                holder.clv.setDataSourceAndPlay(Uri.parse(Constants.VIDEO_URL), false)
                holder.clv.setDataSourceAndPlay(R.raw.wechatsight1, false)
//                        .bindLifecycle(ParallaxActivity.this.getLifecycle())
                ;
            } else {
                holder.clv.setDataSourceAndPlay(R.raw.wechatsight1, false)
//                        .bindLifecycle(ParallaxActivity.this.getLifecycle())
                ;
            }
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        ChaplinVideoView clv;
        boolean isFirstBoot;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
            clv = itemView.findViewById(R.id.clv);
        }

        MyViewHolder(@NonNull View itemView, boolean isFirstBoot) {
            super(itemView);
            this.isFirstBoot = isFirstBoot;
            tvText = itemView.findViewById(R.id.tv_text);
            clv = itemView.findViewById(R.id.clv);
        }
    }

}
