package com.hawksjamesf.uicomponent;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hawksjamesf.uicomponent.adapter.CarouselPagerAdapter;
import com.hawksjamesf.uicomponent.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class ViewPagerActivity extends AppCompatActivity {
    List<Integer> list = new ArrayList<Integer>() {
        {
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }
    };

    CarouselView cv;
    VideoView vv;
//    ChaplinVideoView clv;
    PagerView pv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        cv = findViewById(R.id.cv);
        Adapter adapter = new Adapter();
        cv.setAdapter(adapter);
        cv.setPageTransformer(new ZoomOutPageTransformer());
        adapter.setDataList(list);

        vv = findViewById(R.id.vv);
//        MediaController mediaController = new MediaController(this);
//        vv.setMediaController(mediaController);
//        vv.setVideoURI(Uri.parse(Constants.VIDEO_URL));
//        clv = findViewById(R.id.clv);
//        vv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clv.start();
//            }
//        });
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
            }
        });


        RecyclerView rvContral = findViewById(R.id.rv_contral);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 1) {
                    return 1;
                }
                return 3;
            }
        });
        rvContral.setLayoutManager(gridLayoutManager);
        rvContral.setAdapter(new RecyclerView.Adapter() {
            List<String> datalist = new ArrayList<String>() {
                {
                    add("change");
                    add("top");
                    add("top|center_horizatil");
                    add("top|right");
                    add("start|center_vertical");
                    add("center");
                    add("end|center_vertical");
                    add("bottom");
                    add("bottom|center_horizontal");
                    add("bottom|end");
                }
            };
            List<Integer> datalist2 = new ArrayList<Integer>() {
                {
                    add(LinearLayout.HORIZONTAL);
                    add(Gravity.TOP);
                    add(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                    add(Gravity.TOP | Gravity.END);
                    add(Gravity.START | Gravity.CENTER_VERTICAL);
                    add(Gravity.CENTER);
                    add(Gravity.END | Gravity.CENTER_VERTICAL);
                    add(Gravity.BOTTOM);
                    add(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                    add(Gravity.BOTTOM | Gravity.END);
                }
            };

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(
                        ViewPagerActivity.this.getLayoutInflater().inflate(R.layout.item_control, parent, false)
                ) {


                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                Button bt = (Button) holder.itemView.findViewById(R.id.bt);
                bt.setText(datalist.get(position));
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0) {
                            if (pv.getOrientation() == LinearLayout.VERTICAL) {
                                pv.setOrientation(LinearLayout.HORIZONTAL);
                            } else {
                                pv.setOrientation(LinearLayout.VERTICAL);
                            }
                        }/* else if (position == 1) {//top
                        } else if (position == 2) {
                        } else if (position == 3) {
                        } else if (position == 4) {

                        }*/ else {
//                            pv.setGravity(datalist2.get(position));
                        }


                    }
                });

            }

            @Override
            public int getItemCount() {
                return datalist.size();
            }
        });

        Drawable drawable = getResources().getDrawable(R.drawable.baseline_3d_rotation_black_24, null);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        RotateDrawable rotateDrawable = build_rotatedrawable(drawable,
//                0, 360,
//                10000/2, 10000/2,
//                true, true
//
//        );
//        rotateDrawable.setLevel(10000/2);
//        ((ImageView) findViewById(R.id.iv_compunt)).setImageDrawable(rotateDrawable);
        RotateDrawable rotateDrawable = (RotateDrawable) ((TextView) findViewById(R.id.tv_compunt)).getCompoundDrawables()[2];
//        ((TextView) findViewById(R.id.tv_compunt)).setCompoundDrawables(null,null,rotateDrawable,null);
        rotateDrawable.setLevel(10000 / 2);
//        ((TextView) findViewById(R.id.tv_compunt)).setBackground(rotateDrawable);

    }

    public static RotateDrawable build_rotatedrawable(final Drawable drawable,
                                                      final int fromDegress, final int toDegress,
                                                      final float pivotX, final float pivotY,
                                                      final boolean relativeX, final boolean relativeY) {
        RotateDrawable rotateDrawable = new RotateDrawable();
        rotateDrawable.setDrawable(drawable);
        rotateDrawable.setFromDegrees(fromDegress);
        rotateDrawable.setToDegrees(toDegress);
        rotateDrawable.setPivotX(pivotX);
        rotateDrawable.setPivotY(pivotY);
        rotateDrawable.setPivotXRelative(relativeX);
        rotateDrawable.setPivotYRelative(relativeY);
        rotateDrawable.setLevel(10000 / 2);
        return rotateDrawable;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vv.start();
//        clv.start();

    }

    public static class Adapter extends CarouselPagerAdapter<Integer> {
        public int getPagers() {
            return dataList.size();
        }

        @Override
        protected Object instantiatePager(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            position %= 5;
            if (position == 0) {
                imageView.setBackgroundColor(Color.BLUE);
            } else if (position == 1) {
                imageView.setBackgroundColor(Color.BLACK);
            } else if (position == 2) {
                imageView.setBackgroundColor(Color.YELLOW);
            } else if (position == 3) {
                imageView.setBackgroundColor(Color.RED);
            } else if (position == 4) {
                imageView.setBackgroundColor(Color.CYAN);

            }
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            container.addView(imageView);
            return imageView;
        }

        @Override
        protected void destroyPager(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
