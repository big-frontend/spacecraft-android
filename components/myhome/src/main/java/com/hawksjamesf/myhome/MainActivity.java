package com.hawksjamesf.myhome;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hawksjamesf.av.VideoActivity;
import com.hawksjamesf.common.util.ActivityUtil;
import com.hawksjamesf.myhome.ui.person.SettingsActivity;
import com.hawksjamesf.uicomponent.PhotoActivity;

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        Fragment fragment = WebViewFragment.newInstance("https://i.meituan.com/c/ZDg0Y2FhNjMt");
//        FragmentUtils.add(getSupportFragmentManager(),fragment,android.R.id.content);
//        FragmentUtils.show(fragment);
        WebViewActivity.startActivity(this);
    }

    @Override
    protected void initComponent(@Nullable Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        Uri uri = Uri.parse("https://i.spacecraft.com/c/ZDg0Y2FhNjMt");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        Intent intent =  new Intent();
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(uri);
//        Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER,uri);
        startActivity(intent);
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
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    mTextMessage.setText("home");
//                    ActivityUtil.startActivity(MainActivity.this, ViewPagerActivity.class);
                    // 1. Simple jump within application (Jump via URL in 'Advanced usage')
                    ARouter.getInstance().build("/account/login").navigation();

                    // 2. Jump with parameters
//                    ARouter.getInstance().build("/login/1")
//                            .withLong("key1", 666L)
//                            .withString("key3", "888")
//                            .navigation();
                    return true;
                } else if (itemId == R.id.navigation_dashboard) {
                    mTextMessage.setText("photo");
                    ActivityUtil.startActivity(MainActivity.this, PhotoActivity.class);
                    return true;
                } else if (itemId == R.id.navigation_notifications) {
                    mTextMessage.setText("video");
                    ActivityUtil.startActivity(MainActivity.this, VideoActivity.class);
                    return true;
                } else if (itemId == R.id.navigation_settings) {
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
