package com.hawksjamesf.spacecraft.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hawksjamesf.common.util.ActivityUtil;
import com.hawksjamesf.spacecraft.R;
import com.hawksjamesf.spacecraft.photo.PhotoActivity;
import com.hawksjamesf.spacecraft.ui.person.SettingsActivity;
import com.hawksjamesf.spacecraft.video.VideoActivity;
import com.hawksjamesf.spacecraft.viewpager.ViewPagerActivity;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;
    private Toolbar tb;
    private BottomNavigationView bnv;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void initComponent(@Nullable Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        setContentView(R.layout.activity_main);
        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        tb.setBackgroundColor(Color.RED);
        mTextMessage = findViewById(R.id.message);
        mTextMessage.setText("home");
        bnv = findViewById(R.id.bnv);
        final NavController navController = new NavController(this);
//        navController.navigate(R.id.action_step_two);
//        navController.navigate(R.id.fragment_flow_step_one_dest);
//        navController.navigate(R.id.action_step_two, null, options);
//        navController.setGraph();
//        NavigationUI.setupWithNavController(bnv,navController);

    }

    @Override
    protected void handleCallback(@NotNull Function1<? super Disposable, Unit> autoDisposable) {
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mTextMessage.setText("home");
                        ActivityUtil.startActivity(MainActivity.this, ViewPagerActivity.class);
                        return true;
                    case R.id.navigation_dashboard:
                        mTextMessage.setText("photo");
                        ActivityUtil.startActivity(MainActivity.this, PhotoActivity.class);
                        return true;
                    case R.id.navigation_notifications:
                        mTextMessage.setText("video");
                        ActivityUtil.startActivity(MainActivity.this, VideoActivity.class);
                        return true;
                    case R.id.navigation_settings:
                        ActivityUtil.startActivity(MainActivity.this, SettingsActivity.class);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void loadData(@NotNull Function1<? super Disposable, Unit> autoDisposable) {

    }

}
