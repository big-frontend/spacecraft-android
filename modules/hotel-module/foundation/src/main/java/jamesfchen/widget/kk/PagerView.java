package jamesfchen.widget.kk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: jamesfchen@gmail.com
 * @since: Feb/16/2019  Sat
 * <p>
 */
public class PagerView extends LinearLayout {
    private RecyclerView mRvContent;
    private TabsLayout mTabsLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private SnapHelper mSnapHelper;
    public PagerView(@NonNull Context context) {
        this(context, null);
    }

    public PagerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PagerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int layoutOrientation = getOrientation();
        int scrollOrientation = layoutOrientation == LinearLayout.VERTICAL ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL;
        mTabsLayout = new TabsLayout(context);
        mTabsLayout.setOrientation(scrollOrientation);
        LinearLayout.LayoutParams tablp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTabsLayout.addOnTabSelectedListener((view, position) -> {
            if (mOnTabSelectedListenerList == null) return;
            for (TabsLayout.OnTabSelectedListener l : mOnTabSelectedListenerList) {
                if (l != null) {
                    l.onTabSelected(view, position);
                }
            }
        });
        addView(mTabsLayout, tablp);

        mRvContent = new NestedRecyclerView(context);
        mLinearLayoutManager = new LinearLayoutManager(context, scrollOrientation, false);
        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(mRvContent);
        mRvContent.setLayoutManager(mLinearLayoutManager);

        LinearLayout.LayoutParams rvlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (layoutOrientation == HORIZONTAL){
            rvlp.width =0;
            rvlp.weight =1;
        }
        addView(mRvContent, rvlp);
        RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mTabsLayout.animateIndicatorToPosition(mLinearLayoutManager.findFirstCompletelyVisibleItemPosition(), 200);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };
        mRvContent.addOnScrollListener(listener);
        mTabsLayout.addOnTabSelectedListener((view, position) -> {
            mLinearLayoutManager.scrollToPosition(position);
        });
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
