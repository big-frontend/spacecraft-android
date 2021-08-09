package com.jamesfchen.bundle2.fragmentx;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jamesfchen.bundle2.R;

/**
 *
 *  对于ViewPager来说
 *  item:Inner1Fragment
 *  parent manage:  in HostCallbacks
 *  child manage: in Inner1Fragment
 *
 * 对于RecyclerView来说
 * item:Inner2Fragment
 * parent manage：in HostCallbacks
 * child manage：in Inner2Fragment
 *
 * issue:Fragment has not been attached yet.
 * 在RecyclerView的Adapter中静态注册Fragment导致的，需要效仿ViewPager的Adapter动态注册
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    FragmentManager fm;
    Fragment fragment;

    public RecyclerViewAdapter(FragmentManager fm) {
        this.fm = fm;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        fragment = fm.findFragmentById(R.id.fragment_inner0);
//        if (fragment == null) return;
//        Log.d("FragmentNotAttach", "onBindViewHolder:" + fragment+"/"+fragment.getParentFragmentManager()+"/"+fragment.getChildFragmentManager());
        fm.beginTransaction()
                .replace(R.id.fl_container,Inner2Fragment.newInstance(),"Inner2Fragment")
                .commit();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}
