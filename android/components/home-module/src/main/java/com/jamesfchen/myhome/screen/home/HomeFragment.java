package com.jamesfchen.myhome.screen.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jamesfchen.mvp.RxFragment;
import com.jamesfchen.myhome.GalleryActivity;
import com.jamesfchen.myhome.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class HomeFragment extends RxFragment<HomePresenter> implements HomeContract.View {
    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    RecyclerView rv;
    ListView lv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavOptions options = new NavOptions.Builder()
//                .setEnterAnim(R.anim.)
//                .setExitAnim(R.anim.)
//                .setPopEnterAnim(R.anim.)
//                .setPopExitAnim(R.anim.)
                .build();
        final NavController navController = new NavController(getActivity());
//        navController.navigate(R.id.action_step_two);
//        navController.navigate(R.id.fragment_flow_step_one_dest);
//        navController.navigate(R.id.action_step_two, null, options);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            NavDestination currentDestination = navController.getCurrentDestination();
            NavGraph graph = navController.getGraph();
//                navController.navigateUp()
        });

        Navigation.setViewNavController(view, navController);
        view.findViewById(R.id.bt_navigate_destination).setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.fragment_flow_step_one_dest, null, options));
//        view.findViewById(R.id.bt_navigate_action).setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.action_step_two)
//        );


        AppBarLayout abl = view.findViewById(R.id.abl);
        abl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            }
        });
        CollapsingToolbarLayout ctbl = view.findViewById(R.id.ctbl);
        rv = view.findViewById(R.id.rv);
        rv.setAdapter(new MyAdapter());
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), RecyclerView.VERTICAL, false));
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) rv.getLayoutParams();
        AppBarLayout.ScrollingViewBehavior behavior = (AppBarLayout.ScrollingViewBehavior) layoutParams.getBehavior();
        behavior.setOverlayTop(120);

        lv = view.findViewById(R.id.lv);
        lv.setNestedScrollingEnabled(true);
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
                    View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_image_and_text, parent, false);
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
    }

     class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_and_text, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tvText.setText("asfasdfas");
            holder.tvText.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), GalleryActivity.class);
                startActivity(i);
            });
        }

        @Override
        public int getItemCount() {
            return 13;
        }
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }
}
