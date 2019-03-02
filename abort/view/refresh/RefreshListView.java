package com.hawksjamesf.spacecraft.ui.home.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hawksjamesf.spacecraft.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class RefreshListView extends ListView /*implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener*/{

    private int startY = -1;
    private View listview_refresh_header;
    private ImageView iv_refresh_arrow;
    private ProgressBar pb_refresh_circle;
    private TextView tv_refresh_intro;
    private TextView tv_refresh_currentDate;
    private static final int STATE_PULL_REFRESH = 0;// 下拉刷新
    private static final int STATE_RELEASE_REFRESH = 1;// 松开刷新
    private static final int STATE_REFRESHING = 2;// 正在刷新
    private int mCurrentState = STATE_PULL_REFRESH;// 当前状态;
    private RotateAnimation upAnim;
    private RotateAnimation downAnim;
    private OnRefreshListener mListener;//向外部提供的接口
    private int mFooterViewHeight;
    private View listview_refresh_footer;
    private int mHeaderViewHeight;

    public RefreshListView(Context context) {
        super(context);
        init();
    }


    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        initHeaderView();
        initArrowAnim();
//        initFooterView();

    }

    /*
    初始化头布局
     */
    private void initHeaderView() {
        listview_refresh_header = View.inflate(getContext(), R.layout.listview_refresh_header, null);
        //初始化头布局里的控件
        iv_refresh_arrow = (ImageView) listview_refresh_header.findViewById(R.id.iv_refresh_arrow);
        pb_refresh_circle = (ProgressBar) listview_refresh_header.findViewById(R.id.pb_refresh_circle);
        tv_refresh_intro = (TextView) listview_refresh_header.findViewById(R.id.tv_refresh_intro);
        tv_refresh_currentDate = (TextView) listview_refresh_header.findViewById(R.id.tv_refresh_currentDate);
        //先添加的布局在最上面，后添加在下面
        this.addHeaderView(listview_refresh_header);
        listview_refresh_header.measure(0, 0);//量取大小
        mHeaderViewHeight = listview_refresh_header.getMeasuredHeight();//获得大小
        listview_refresh_header.setPadding(0, -mHeaderViewHeight, 0, 0);//隐藏头布局
        tv_refresh_currentDate.setText(getCurrentDate());
    }

    /*
    初始化脚布局
     */
    private void initFooterView() {
        listview_refresh_footer = View.inflate(getContext(), R.layout.listview_refresh_footer, null);
        this.addFooterView(listview_refresh_footer);
        listview_refresh_footer.measure(0, 0);//量取大小
        mFooterViewHeight = listview_refresh_footer.getMeasuredHeight();//获得大小
        listview_refresh_footer.setPadding(0, -mFooterViewHeight, 0, 0);//隐藏脚布局
//        setOnScrollListener(this);
    }

    /*
    初始化动画
     */
    private void initArrowAnim() {
        //向上的动画
        upAnim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnim.setDuration(200);
        upAnim.setFillAfter(true);
        //向下的动画
        downAnim = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnim.setFillAfter(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {//ACTION_DOWN可能不响应,确保startY有效
                    startY = (int) ev.getRawY();
                }
                if (mCurrentState == STATE_REFRESHING) {
                    break;//当前为下拉刷新时跳出语句，不做处理
                }
                int endY = (int) ev.getRawY();
                int dy = endY - startY;//移动的距离
                if (dy > 0 && getFirstVisiblePosition() == 0) {//只有下拉并且当前是第一个item,才允许下拉
                    int paddingHeight = dy - mHeaderViewHeight;
                    listview_refresh_header.setPadding(0, paddingHeight, 0, 0);//设置当前padding

                    if (paddingHeight < 0 && mCurrentState != STATE_PULL_REFRESH) {//改为下拉刷新
                        mCurrentState = STATE_PULL_REFRESH;
                        refreshState();

                    } else if (paddingHeight > 0 && mCurrentState != STATE_RELEASE_REFRESH) {//改为松开刷新
                        mCurrentState = STATE_RELEASE_REFRESH;
                        refreshState();
                    }
                    return true;//避免其他事件处理他

                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;//重置
                if (mCurrentState == STATE_RELEASE_REFRESH) {
                    //当前状态松开刷新时立即变为正在刷新,显示listview_refresh_header
                    mCurrentState = STATE_REFRESHING;//正在刷新
                    listview_refresh_header.setPadding(0, 0, 0, 0);
                    refreshState();
                } else if (mCurrentState == STATE_PULL_REFRESH) {
                    //当前状态下拉刷新时立即隐藏listview_refresh_header
                    listview_refresh_header.setPadding(0, -mHeaderViewHeight, 0, 0);

                }

                break;

        }
        return super.onTouchEvent(ev);//让父类处理
    }

    /*
     刷新下拉控件的布局
    */
    private void refreshState() {
        switch (mCurrentState) {
            case STATE_PULL_REFRESH://下拉刷新
                iv_refresh_arrow.setVisibility(View.VISIBLE);
                pb_refresh_circle.setVisibility(View.INVISIBLE);
                tv_refresh_intro.setText("pull ...");
                iv_refresh_arrow.startAnimation(downAnim);//启动跳转箭头的动画
                break;
            case STATE_RELEASE_REFRESH://松开刷新
                iv_refresh_arrow.setVisibility(View.VISIBLE);
                pb_refresh_circle.setVisibility(View.INVISIBLE);
                tv_refresh_intro.setText("release ...");
                iv_refresh_arrow.startAnimation(upAnim);//启动跳转箭头的动画

                break;
            case STATE_REFRESHING://正在刷新
                //必须清除动画才能隐藏
                iv_refresh_arrow.clearAnimation();
                iv_refresh_arrow.setVisibility(View.INVISIBLE);
                pb_refresh_circle.setVisibility(View.VISIBLE);
                tv_refresh_intro.setText("refreshing ...");
                if (mListener != null) {

                        mListener.onRefresh();
                }
                break;
        }
    }

//    private boolean isLoadingMore;

//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
//            if (getLastVisiblePosition() == getCount() - 1 && !isLoadingMore) {//滑动到了底部
//                System.out.println("到底了");
//                listview_refresh_footer.setPadding(0, 0, 0, 0);//显示脚布局
//                setSelection(getCount() - 1);// 改变listview显示位置
//                isLoadingMore = true;
//                if (mListener != null) {
//                    mListener.onLoadMore();//加载更多数据
//                }
//
//            }
//
//        }
//
//    }

//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//    }
//    private OnItemClickListener mItemClickListener;
//    @Override
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        super.setOnItemClickListener(this);
//        mItemClickListener =listener;
////    }
//    @Override//重写该方法让TabDetailPager获得的position不受头布局的干扰
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (mItemClickListener !=null){
//        mItemClickListener.onItemClick(parent,view,position-getHeaderViewsCount(),id);
//        }
//    }


    //向外部提供监听的接口
    public interface OnRefreshListener {
        public void onRefresh();

//        public void onLoadMore();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    //获取当前时间
    public String getCurrentDate() { //m表示一月从0开始，M表示一月从1开始。h表示12时制，H表示24时制。
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        System.out.println("update date：" + format.format(new Date()));
        return format.format(new Date());
    }

    //收起下拉刷新控件
    public void onRefreshComplete(boolean success) {
//        if (isLoadingMore) {//加载更多
//            listview_refresh_footer.setPadding(0, -mFooterViewHeight, 0, 0);//隐藏脚布局
//            isLoadingMore = false;
//        } else {
            mCurrentState = STATE_PULL_REFRESH;
            iv_refresh_arrow.setVisibility(View.VISIBLE);
            pb_refresh_circle.setVisibility(View.INVISIBLE);
            tv_refresh_intro.setText("pull ...");
            listview_refresh_header.setPadding(0, -mHeaderViewHeight, 0, 0);
            if (success) {//如果加载成功，则更新时间
                tv_refresh_currentDate.setText("last refresh date : " + getCurrentDate());
            }
//        }
    }
}
