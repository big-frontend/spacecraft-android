package jamesfchen.widget.kk;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jamesfchen.loader.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: jamesfchen@gmail.com
 * @since: Feb/16/2019  Sat
 * <p>
 * 实现了嵌套RecyclerView+RecyclerView同方向的滑动冲突
 */
public class PagerView extends CoordinatorLayout {
    private RecyclerView mRvContent;
    private TabsLayout mTabsLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private SnapHelper mSnapHelper;
    public static final int HORIZONTAL = RecyclerView.HORIZONTAL;
    public static final int VERTICAL = RecyclerView.VERTICAL;
    private int mOrientation = HORIZONTAL;

    public PagerView(@NonNull Context context) {
        this(context, null);
    }

    public PagerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PagerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerView, defStyleAttr, 0);
        mOrientation = typedArray.getInteger(R.styleable.PagerView_pv_orientation, HORIZONTAL);
        typedArray.recycle();

        mTabsLayout = new TabsLayout(context);
        mTabsLayout.setOrientation(mOrientation);
        CoordinatorLayout.LayoutParams tablp = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTabsLayout.addOnTabSelectedListener((view, position) -> {
            if (mOnTabSelectedListenerList == null) return;
            for (TabsLayout.OnTabSelectedListener l : mOnTabSelectedListenerList) {
                if (l != null) {
                    l.onTabSelected(view, position);
                }
            }
        });
        addView(mTabsLayout, -1, tablp);

        mRvContent = new NestedRecyclerView(context);
        mLinearLayoutManager = new LinearLayoutManager(context, mOrientation, false);
        if (mOrientation == HORIZONTAL) {
            mSnapHelper = new PagerSnapHelper();
            mSnapHelper.attachToRecyclerView(mRvContent);
        }
        mRvContent.setLayoutManager(mLinearLayoutManager);

        CoordinatorLayout.LayoutParams rvlp = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rvlp.setBehavior(new NestedScrollingBehavior(context, attrs));
        addView(mRvContent, -1, rvlp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentMeasuredHeight = getMeasuredHeight();
        int parentMeasuredWidth = getMeasuredWidth();
        if (mOrientation == HORIZONTAL) {
            parentMeasuredHeight = mTabsLayout.getMeasuredHeight() + mRvContent.getMeasuredHeight() + getPaddingTop() + getPaddingBottom();
        } else {
            parentMeasuredWidth = mTabsLayout.getMeasuredWidth() + mRvContent.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
        }
        final int width = View.resolveSizeAndState(parentMeasuredWidth, widthMeasureSpec,
                getMeasuredState());
        final int height = View.resolveSizeAndState(parentMeasuredHeight, heightMeasureSpec,
                getMeasuredState());
        setMeasuredDimension(width, height);
    }

    private List<TabsLayout.OnTabSelectedListener> mOnTabSelectedListenerList;

    public void addOnTabSelectedListener(TabsLayout.OnTabSelectedListener onTabSelectedListener) {
        if (mOnTabSelectedListenerList == null) {
            mOnTabSelectedListenerList = new ArrayList<>();
        }
        mOnTabSelectedListenerList.add(onTabSelectedListener);
    }

    public void setAdapter(@NonNull final Adapter adapter) {
        mRvContent.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                mTabsLayout.setDataList(adapter.getTabList());
            }
        });
    }

    public static abstract class Adapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
        public abstract List<TabsLayout.TabItem> getTabList();

    }
}
