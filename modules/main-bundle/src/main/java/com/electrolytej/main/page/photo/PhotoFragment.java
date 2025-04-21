package com.electrolytej.main.page.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.electrolytej.main.R;
import com.electrolytej.main.image.model.Photo;

import java.security.MessageDigest;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/06/2018  Tue
 * <p>
 * bitmap，drawable、uri、resource id
 */
public class PhotoFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PAGE = "page";
    Photo page;
    int sectionNumber = -1;
    ImageView ivPhoto;
    TextView tvText;

    public static PhotoFragment newInstance(int sectionNumber, Photo page) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putParcelable(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments == null) return;
        page = arguments.getParcelable(ARG_PAGE);
        sectionNumber = arguments.getInt(ARG_SECTION_NUMBER);
        ivPhoto = view.findViewById(R.id.iv_photo);
        tvText = view.findViewById(R.id.tv_text);

        if (page.uri == null) {
            tvText.setText(String.valueOf(sectionNumber));
            tvText.setVisibility(View.VISIBLE);
            ivPhoto.setVisibility(View.GONE);
        } else {
            tvText.setVisibility(View.GONE);
            ivPhoto.setVisibility(View.VISIBLE);
            Glide.with(ivPhoto.getContext())
                    .asBitmap()
                    .load(page.uri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(new FitCenter(), new ReflectionTransform())
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Log.d(Constants.TAG_PHOTO_ACTIVITY, Log.getStackTraceString(e));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d(Constants.TAG_PHOTO_ACTIVITY, "index:" + sectionNumber + " bitmap size:" + (resource.getByteCount() / 1024f) + "k");
                            return false;
                        }
                    })
                    .into(ivPhoto);

        }
//        final ViewOffsetHelper viewOffsetHelper = ViewUtil.getViewOffsetHelper(ivPhoto);
        final int touchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
//        ivPhoto.setOnDragListener(new View.OnDragListener() {
//
//            public boolean onDrag(View vv, DragEvent event) {
//                ImageView v = (ImageView) vv;
//                final int action = event.getAction();
//
//                switch(action) {
//
//                    case DragEvent.ACTION_DRAG_STARTED:
//
//                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
//                            v.setColorFilter(Color.BLUE);
//
//                            v.invalidate();
//                            return true;
//
//                        }
//                        return false;
//
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        v.setColorFilter(Color.GREEN);
//                        v.invalidate();
//                        return true;
//
//                    case DragEvent.ACTION_DRAG_LOCATION:
//                        return true;
//
//                    case DragEvent.ACTION_DRAG_EXITED:
//                        v.setColorFilter(Color.BLUE);
//                        v.invalidate();
//
//                        return true;
//
//                    case DragEvent.ACTION_DROP:
//                        ClipData.Item item = event.getClipData().getItemAt(0);
//                        Toast.makeText(getActivity(), "Dragged data is " + item.getText(), Toast.LENGTH_LONG).show();
//                        v.clearColorFilter();
//                        v.invalidate();
//                        return true;
//
//                    case DragEvent.ACTION_DRAG_ENDED:
//                        v.clearColorFilter();
//                        v.invalidate();
//                        if (event.getResult()) {
//                            Toast.makeText(getActivity(), "The drop was handled.", Toast.LENGTH_LONG).show();
//
//                        } else {
//                            Toast.makeText(getActivity(), "The drop didn't work.", Toast.LENGTH_LONG).show();
//
//                        }
//                        return true;
//
//                    default:
//                        Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
//                        break;
//                }
//
//                return false;
//            }
//        });
//
//        ivPhoto.setOnLongClickListener(new View.OnLongClickListener() {
//
//            public boolean onLongClick(View v) {
//                ClipData.Item item = new ClipData.Item(" photo item tag");
//                ClipData dragData = new ClipData(
//                        "photo item tag",
//                        new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
//                        item);
//                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(ivPhoto);
//                v.startDrag(dragData,  // the data to be dragged
//                        myShadow,  // the drag shadow builder
//                        null,      // no need to use local data
//                        0          // flags (not currently used, set to 0)
//                );
//                return true;
//            }
//        });
//        ivPhoto.setOnTouchListener(new View.OnTouchListener() {
//
//            private float preX, preY;
//            private int baseDeltaX, baseDeltaY;
//            private int deltaX, deltaY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        preX = event.getRawX();
//                        preY = event.getRawY();
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        deltaX = (int) (event.getRawX() - preX) + baseDeltaX;
//                        deltaY = (int) (event.getRawY() - preY) + baseDeltaY;
//                        if (deltaX != 0) viewOffsetHelper.setLeftAndRightOffset(deltaX);
//                        if (deltaY != 0) viewOffsetHelper.setTopAndBottomOffset(deltaY);
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        baseDeltaX = deltaX;
//                        baseDeltaY = deltaY;
//                        return true;
//                    case MotionEvent.ACTION_CANCEL:
//                        return true;
//                }
//
//                return true;
//            }
//        });
    }

    private static class MyDragShadowBuilder extends View.DragShadowBuilder {

        private static Drawable shadow;

        public MyDragShadowBuilder(View v) {
            super(v);
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            int width = getView().getWidth() / 2;
            int height = getView().getHeight() / 2;
            shadow.setBounds(0, 0, width, height);
            size.set(width, height);
            touch.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            shadow.draw(canvas);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    static class ReflectionTransform extends BitmapTransformation {
        private final String ID = "com.electrolytej.main.screen.photo.PhotoFragment$ReflectionTransform";
        private final byte[] ID_BYTES = ID.getBytes(CHARSET);

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap decoded, int outWidth, int outHeight) {
            Log.d(Constants.TAG_PHOTO_ACTIVITY, "ReflectionTransform:transform:" + outWidth + "/" + outHeight);
            try {
                //"内存溢出"
//                throw  new OutOfMemoryError("溢出");
                return ImageUtils.addReflection(decoded, 360);
            } catch (OutOfMemoryError e) {
                pool.clearMemory();
                return decoded;
            }
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(ID_BYTES);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof ReflectionTransform;
        }

        @Override
        public int hashCode() {
            return ID.hashCode();
        }

    }
}
