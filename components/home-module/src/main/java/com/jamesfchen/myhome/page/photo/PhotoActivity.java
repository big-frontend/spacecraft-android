package com.jamesfchen.myhome.page.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamesfchen.myhome.R;
import com.jamesfchen.myhome.image.model.Photo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.viewpager.widget.ViewPager;

/**
 *三种内存策略
 * 低 < 70
 * 中  70< .. <130
 * 搞  > 130
 * 根据这三个内存策略可以压缩(采样率，宽高、质量)不同质量的图片
 */
public class PhotoActivity extends AppCompatActivity {


    public static final String EXTRA_THUMBNAIL_URLLIST = "thumbnailUrlList";
    public static final String EXTRA_URLLIST = "urlList";
    public static final String EXTRA_CURPOSITION = "curPosition";
    private static final int threshold = 2;
    private ArrayList<Uri> mUrlList;
    private int curPosition;
    public static final String IV_TRANSITIONNAME = "image";
    public static final String TV_TRANSITIONNAME = "text";


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    //android binder 大小限制：525k是最为安全的，512k-1M 可能出错或者闪退，大于1m throw exception。
    public static Intent newIntent(@NonNull final Activity activity, final ArrayList<Uri> urlList, final int curPosition) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_URLLIST, urlList);
//        intent.putStringArrayListExtra(EXTRA_THUMBNAIL_URLLIST, thumbnailUriList);
        intent.putExtra(EXTRA_CURPOSITION, curPosition);
        return intent;
    }

    public static void startActivity(Activity activity, final ArrayList<Uri> urlList, final int curPosition) {
        ActivityCompat.startActivity(activity, newIntent(activity, urlList, curPosition), null);
    }

    public static void startActivityWithSharedElement(
            Activity activity, ImageView iv, TextView tv,
            final ArrayList<Uri> urlList, final int curPosition) {
        Pair<View, String> pair0 = Pair.create((View) iv, IV_TRANSITIONNAME);
        Pair<View, String> pair1 = Pair.create((View) tv, TV_TRANSITIONNAME);
        ActivityCompat.startActivity(
                activity,
                newIntent(activity, urlList, curPosition),
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair0, pair1).toBundle());
    }

    public static void startActivityWithCustom(Activity activity, int enterResId, int exitResId, final ArrayList<Uri> urlList, final int curPosition) {
        ActivityCompat.startActivity(
                activity,
                newIntent(activity, urlList, curPosition),
                ActivityOptionsCompat.makeCustomAnimation(activity, enterResId, exitResId).toBundle());
    }

    public static void startActivityWithClipReveal(
            Activity activity, View source,
            int startX, int startY, int startWidth, int startHeight,
            final ArrayList<Uri> urlList, final int curPosition) {
        ActivityCompat.startActivity(
                activity,
                newIntent(activity, urlList, curPosition),
                ActivityOptionsCompat.makeClipRevealAnimation(source, startX, startY, startWidth, startHeight).toBundle());

    }

    public static void startActivityWithScaleUp(
            Activity activity, View source,
            int startX, int startY, int startWidth, int startHeight,
            final ArrayList<Uri> urlList, final int curPosition) {
        ActivityCompat.startActivity(
                activity,
                newIntent(activity, urlList, curPosition),
                ActivityOptionsCompat.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight).toBundle());

    }

    public static void startActivityWithThumbnailScaleUp(
            Activity activity, View source, Bitmap thumbnail,
            int startX, int startY, final ArrayList<Uri> urlList,
            final int curPosition) {
        ActivityCompat.startActivity(
                activity, newIntent(activity, urlList, curPosition),
                ActivityOptionsCompat.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrlList = getIntent().getParcelableArrayListExtra(EXTRA_URLLIST);
        curPosition = getIntent().getIntExtra(EXTRA_CURPOSITION, 0);
        int urlSize = mUrlList.size();
        ArrayList<Photo> pages = new ArrayList<Photo>(urlSize);
        for (int i = 0; i < urlSize; ++i) {
            pages.add(new Photo(null, mUrlList.get(i)));
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
    //window visibiliy
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

}
