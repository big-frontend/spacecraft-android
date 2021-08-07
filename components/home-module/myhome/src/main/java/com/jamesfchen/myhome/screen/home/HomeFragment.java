package com.jamesfchen.myhome.screen.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamesfchen.myhome.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;


/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

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


    }
}
