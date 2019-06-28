package com.hawksjamesf.spacecraft;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.SectionIndexer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustListView extends ListView implements SectionIndexer {
    public CustListView(Context context) {
        this(context, null);
    }

    public CustListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    GestureDetector gestureDetector;

    public CustListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                Log.d("CustListView", "onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d("CustListView", "onShowPress");

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d("CustListView", "onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d("CustListView", "onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d("CustListView", "onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d("CustListView", "onFling");
                return false;
            }
        });

        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
        gestureDetector.setIsLongpressEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gestureDetector.setContextClickListener(new GestureDetector.OnContextClickListener() {
                @Override
                public boolean onContextClick(MotionEvent e) {
                    return false;
                }
            });
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return gestureDetector.onTouchEvent(ev);
//    }

//    @Override
//    public boolean onGenericMotionEvent(MotionEvent event) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            return gestureDetector.onGenericMotionEvent(event);
//        }else {
//            return super.onGenericMotionEvent(event);
//        }
//    }


    @Override
    protected void layoutChildren() {


//                Debug.startMethodTracing("AbsListViewScroll");
//                mScrollProfilingStarted = true;

        if (aminating) {
//            ReflectUtils reflect = ReflectUtils.reflect(getClass().getSuperclass());
//             reflect.method("fillGap",true);
            try {
                Class<?> superclass = getClass().getSuperclass();
                if (superclass != null) {
                    for (Method method : superclass.getDeclaredMethods()) {
                        Log.d("CustListView", "method name:" + method.getName());
                    }
                    Method fillGap = superclass.getDeclaredMethod("fillGap", boolean.class);
                    fillGap.setAccessible(true);
                    Object invoke = fillGap.invoke(this, true);
                    Log.d("CustListView", "result:" + invoke);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            super.layoutChildren();
        }


//        if (PROFILE_SCROLLING) {
//            if (mScrollProfilingStarted) {
//                Debug.stopMethodTracing();
//                mScrollProfilingStarted = false;
//            }
//        }

    }


    boolean aminating;
    boolean moveUp;

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
