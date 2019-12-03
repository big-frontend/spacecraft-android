package com.hawksjamesf.uicomponent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class PhotoActivity extends AppCompatActivity {


    public static final String EXTRA_THUMBNAILBITMAP = "thumbnailBitmap";
    public static final String EXTRA_URLLIST = "urlList";
    public static final String EXTRA_CURPOSITION = "curPosition";
    private static final int threshold = 2;
    private ArrayList<Bitmap> mThumbnailBitmapList;
    private ArrayList<String> mUrlList;
    private int curPosition;


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public static void startActivity(@NonNull Activity activity, @NonNull final ArrayList<Bitmap> thumbnailBitmapList, final ArrayList<String> urlList, final int curPosition) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        Log.d(Constants.TAG, "size:" + thumbnailBitmapList.size() + "/curPosition:" + curPosition);
        if (thumbnailBitmapList.size() > threshold) {
            for (int i = 0, size = thumbnailBitmapList.size(); i < size; i++) {
                if (curPosition <= i && i < threshold + curPosition) continue;
                thumbnailBitmapList.set(i, null);
            }
        }
        intent.putParcelableArrayListExtra(EXTRA_THUMBNAILBITMAP, thumbnailBitmapList);
        intent.putStringArrayListExtra(EXTRA_URLLIST, urlList);
        intent.putExtra(EXTRA_CURPOSITION, curPosition);
        activity.startActivity(intent);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //也可以在android:theme="@style/PhotoTheme"中配置
//            BarUtil.setStatusBarTransparent(this);
//            BarUtil.setBarsTransparent(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//            BarUtil.setBarsFullscreen(PhotoActivity.this, BarUtil.IMMERSIVE_STICKY);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThumbnailBitmapList = getIntent().getParcelableArrayListExtra(EXTRA_THUMBNAILBITMAP);
        mUrlList = getIntent().getStringArrayListExtra(EXTRA_URLLIST);
        curPosition = getIntent().getIntExtra(EXTRA_CURPOSITION, 0);
        ArrayList<Page> pages = new ArrayList<>(mThumbnailBitmapList.size());
        try {
            for (int i = 0, thumbnailsize = mThumbnailBitmapList.size(), urlSize = mUrlList.size(); i < urlSize; ++i) {
                URL url = new URL(mUrlList.get(i));
                Bitmap bitmap = null;
                if (i < thumbnailsize) {
                    bitmap = mThumbnailBitmapList.get(i);
                }
                pages.add(new Page(bitmap, url));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_photo);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.setDataList(pages);
        mViewPager.setCurrentItem(curPosition, true);

//        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//            @Override
//            public void onSystemUiVisibilityChange(int visibility) {
//                // Note that system bars will only be "visible" if none of the
//                // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
//                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                    // TODO: The system bars are visible. Make any desired
//                    // adjustments to your UI, such as showing the action bar or
//                    // other navigational controls.
//                } else {
//                    // TODO: The system bars are NOT visible. Make any desired
//                    // adjustments to your UI, such as hiding the action bar or
//                    // other navigational controls.
//                }
//            }
//        });
    }


}
