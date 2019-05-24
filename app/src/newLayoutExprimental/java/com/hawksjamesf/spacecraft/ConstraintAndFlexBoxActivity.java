package com.hawksjamesf.spacecraft;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class ConstraintAndFlexBoxActivity extends AppCompatActivity {
    RecyclerView rvOut;
    Adapter adapter;
    ViewPager2 vpOut2;
    ViewPager vpOut;
    List<Integer> list = new ArrayList<Integer>() {{
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_and_flexbox);
        rvOut = findViewById(R.id.rv_out);
        adapter = new Adapter();
        rvOut.setAdapter(adapter);
        rvOut.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvOut.setNestedScrollingEnabled(false);
        rvOut.setHasFixedSize(true);

        vpOut2 = findViewById(R.id.vp2_out);
        vpOut2.setAdapter(adapter);
        vpOut2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        vpOut = findViewById(R.id.vp_out);
        PagerViewAdapter adapter = new PagerViewAdapter();
        vpOut.setAdapter(adapter);

    }

    class PagerViewAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 5;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ViewHolder holder = new ViewHolder(
                    LayoutInflater.from(container.getContext()).inflate(R.layout.item_nest, container, false)
            );


            holder.rvNest.setAdapter(new NestAdapter());
            holder.rvNest.setLayoutManager(new LinearLayoutManager(holder.rvNest.getContext(), RecyclerView.HORIZONTAL, false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.rvNest.getContext(), RecyclerView.HORIZONTAL);
            dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
            holder.rvNest.addItemDecoration(dividerItemDecoration);
//            holder.itemView.setBackground();
            holder.tvPagerName.setText("pager:" + position);
            holder.tvPagerName.setBackgroundColor(Color.CYAN);
            container.addView(holder.itemView);
            return  holder.itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nest, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.rvNest.setAdapter(new NestAdapter());
            holder.rvNest.setLayoutManager(new LinearLayoutManager(holder.rvNest.getContext(), RecyclerView.HORIZONTAL, false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.rvNest.getContext(), RecyclerView.HORIZONTAL);
            dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
            holder.rvNest.addItemDecoration(dividerItemDecoration);
//            holder.itemView.setBackground();
            holder.tvPagerName.setText("pager:" + position);
            holder.tvPagerName.setBackgroundColor(Color.CYAN);

        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rvNest;
        private TextView tvPagerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvNest = itemView.findViewById(R.id.rv_nest);
            tvPagerName = itemView.findViewById(R.id.tv_pager_name);
        }
    }


    public class NestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            return new RecyclerView.ViewHolder(imageView) {
            };

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ImageView imageView = (ImageView) holder.itemView;
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
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }
}
