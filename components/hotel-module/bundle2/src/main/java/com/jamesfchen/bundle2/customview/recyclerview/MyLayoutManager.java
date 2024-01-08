package com.jamesfchen.bundle2.customview.recyclerview;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: May/30/2021  Sun
 *
 * RecyclerView测绘：先测绘所有子View，然后通过子view的测绘宽高+自己的内边距算出一个渴望的宽高desired，当时这个渴望的宽高会根据测量规格调整，
 * 如果测量规格是精确的，那么就用测量规格提供的spec size；如果测量规格是at most，那么会在desired和spec size去最大值；如果是不限制，就取desired。
 *
 * RecyclerView测绘两次(LinearLayoutManger要求)：如果容器的测绘规格是at most or 不限制 并且某个子View为两个方向都为弹性布局，即测绘规格为at most(因为lp.width<0 and lp.height<0)，
 * 然后容器的测绘规格就会被强制改为精确且宽度用上一次测绘的结果，也就是最大子view的宽高。
 *
 *
 */
public class MyLayoutManager extends RecyclerView.LayoutManager{
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        recycler.getViewForPosition()
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
    }
}
