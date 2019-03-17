package com.hawksjamesf.spacecraft;

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

import com.hawksjamesf.common.widget.ChaplinVideoView;
import com.hawksjamesf.common.widget.Constants;
import com.hawksjamesf.common.util.ConvertUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
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
    ListView lv;
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

        item_top_bar_for_recyclerview = findViewById(R.id.item_top_bar_for_recyclerview);
        clvForRecyclerView = item_top_bar_for_recyclerview.findViewById(R.id.clv);
        clvForRecyclerView.setDataSourceAndPlay(Uri.parse(Constants.VIDEO_URL));

        item_top_bar_for_listview = findViewById(R.id.item_top_bar_for_listview);
        clvForListView = item_top_bar_for_listview.findViewById(R.id.clv);


        rv = findViewById(R.id.rv);
        final MyAdapter myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rv.getContext(), RecyclerView.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rv);
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
                if (newState ==RecyclerView.SCROLL_STATE_IDLE){
//                    recyclerView.findViewHolderForAdapterPosition()
//                    recyclerView.findViewHolderForLayoutPosition()
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
//                    moveUpAndDown(v, event);
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
                Log.d("ParallaxActivity", "ListView:getView:position" + position);
                MyViewHolder myViewHolder = null;
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
    private int mDuration = 300;

    private void startTitleViewAnimator(final View v, final int endPaddingTop) {
        if (item_top_bar_for_recyclerview != null && item_top_bar_for_listview != null) {
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
                    }

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("ParallaxActivity", "onCreateViewHolder");
            return new MyViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Log.d("ParallaxActivity", "RecyclerView:onBindViewHolder:position:" + position);
            holder.tvText.setText("RecyclerView:position:" + position);
        }

        @Override
        public int getItemCount() {
            return 23;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        ChaplinVideoView clv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
            clv = itemView.findViewById(R.id.clv);
        }
    }

}
